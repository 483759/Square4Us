package com.ssafy.square4us.common.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * WebSocket 메시지를 핸들링하여 publisher/subscriber 모델을 구현하는 클래스
 */
@Component
@Slf4j
public class SocketHandler extends TextWebSocketHandler {
	List<HashMap<String, Object>> rls = new ArrayList<>(); // 생성한 웹소켓 세션들을 담아둘 리스트

	// 문자열로 들어온 json string을 JSON 객체로 변환하는 파서
	private static JSONObject jsonToObjectParser(String jsonStr) {
		JSONParser parser = new JSONParser();
		try {
			JSONObject obj = (JSONObject) parser.parse(jsonStr);
			return obj;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String msg = message.getPayload();
		JSONObject obj = jsonToObjectParser(msg);

		String cI = (String) obj.get("confId");
		HashMap<String, Object> curConf = new HashMap<String, Object>();
		// 현재 미팅 방 번호를 가져온다

		if (rls.isEmpty()) {
			// 만약 세션이 비어있다면(아무런 방이 생성되지 않았다면) 탈출한다
			return;
		}

		for (HashMap<String, Object> conf : rls) {
			String confId = (String) conf.get("confId");
			if (confId.equals(cI)) {
				curConf = conf;
				break;
			}
		}
		// 현재 미팅 방 번호에 해당하는 웹소켓 세션 리스트를 가져와 curConf에 저장한다

		for (String s : curConf.keySet()) {
			if (s.equals("confId")) {
				continue;
			}

			WebSocketSession wss = (WebSocketSession) curConf.get(s);
			if (wss == null) {
				continue;
			}
			try {
				// curConf의 웹 소켓 세션들에 대해 메시지를 송신한다
				wss.sendMessage(new TextMessage(obj.toJSONString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		//소켓 연결
		super.afterConnectionEstablished(session);

		boolean flag = false;
		String url = session.getUri().toString();
		log.info("Socket Connection To: " + url);

		String cI = url.split("/chat")[1];
		HashMap<String, Object> existingConf = null;
		
		//현재 입장하려는 미팅이 이미 존재하는 방인지 검사한다
		if (!rls.isEmpty()) {
			for (HashMap<String, Object> conf : rls) {
				String confId = (String) conf.get("confId");
				if (confId.equals(cI)) {
					flag = true;
					existingConf = conf;
					break;
				}
			}
		}
		
		if(flag) {
			//이미 존재하는 방이라면 세션에만 추가한다
			HashMap<String, Object> map = existingConf;
			map.put(session.getId(), session);
		}else {
			//최초로 생성하는 방이라면 방 번호와 세션을 추가한다
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("confId", cI);
			map.put(session.getId(), session);
			rls.add(map);
		}
		
		//세션 등록이 끝난 후 발급받은 세션 ID 값의 메시지를 전송한다
		JSONObject obj = new JSONObject();
		obj.put("type", "getId");
		obj.put("sessionId", session.getId());
		session.sendMessage(new TextMessage(obj.toJSONString()));
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		//소켓 종료
		if(!rls.isEmpty()) {
			for (HashMap<String, Object> conf : rls) {
				conf.remove(session.getId());
			}
		}
		super.afterConnectionClosed(session, status);
	}
}
