<template>
<!-- <li style="height:80px"> -->
<div class="articleCard">
  <div class="articleTitleBox">
    <div style="font-size: 12px">
      {{article.id}}번째 글
    </div>
    <div class="articleTitle" >
      {{ article.title }} 
    </div> 
  </div>
  <div class="articleCreatedAt">
    <div>
      작성자: {{article.member.nickname}}
    </div>
    <div>
      작성일: {{dateFormatter(article.createdDate)}}
    </div>
  </div>
</div>
<!-- </li> -->
  <!-- <div class='article-author' >{{ article.member.nickname }} </div> 
  <div class='article-date' >{{ dateFormatter(article.createdDate) }} </div>  -->
  
<!-- <button class='read-button' @click="$emit('onEnter', article.id)">입장</button> -->

</template>

<script>
export default {
  name: 'StudyArticleItem',
  props: {
    article: {
      type: Object,
      required: true
    }
  },
  setup() {
      const dateFormatter = (date)=>{
          var month=date.substring(5,7)
          var day=date.substring(8,10)
          var time=date.substring(11,16)
          return month+'월 '+day+'일 '+time
      }

      return {
          dateFormatter
      }
  }
}
</script>

<style>
.article-title, .article-author, .article-date {
  flex-grow: 1;
  font-size: 0.95rem;
  font-weight: 600;
}
.read-button {
  width: 100px;
  height: 30px;
}
.articleCard {
	display: flex;
	justify-content: space-between;
	flex-basis: 1800px;
	width: 600px;
	height: 60px;

	border: none;
	border-top: 1px solid #195C77;
	border-left: 10px solid #195C77;
	border-right: 1px solid #195C77;
	padding: 5px 0 5px 5px;

	/* border-bottom: 1px solid gray; */
	margin: 0; 
	background-color:white;
	color: gray;

}
.articleTitleBox {
	display: flex;
	flex-direction: column;
	margin: 0 0 0 8px;
}
.articleTitle {
	padding-top: 5px;
	font-weight: bold;
  font-size: 22px;

}
.articleCreatedAt {
	display: flex;
	flex-direction: column ;
  justify-content: center;
	font-size: 14px;
	margin-right: 20px;
}
</style>