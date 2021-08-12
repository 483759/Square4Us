<template>
  <div class="articleCard">
		<div class="articleTitleBox">
			<div style="font-size: 5px">
				{{article.id}}번째 글
			</div>
			<div class="articleTitle">
				{{article.title}}
			</div>
			
		</div>
		
		<!-- {{article.content}} -->
		<div class="articleCreatedAt">
			<div>
				작성자: {{article.nickname}}
			</div>
			<div>
				작성일: {{article.createdDate}}
			</div>
			
		</div>
		
	</div>
	
</template>

<script>


export default {
	name: 'StudyArticleItem',
	props: {
		article: {
			type: Object,
			required: true,
		}
	},
	
}
</script>

<style>
.articleCard {
	display: flex;
	justify-content: space-between;
	flex-basis: 600px;
	width: 600px;
	height: 40px;

	border: none;
	border-top: 1px solid #195C77;
	border-left: 10px solid #195C77;
	border-right: 1px solid #195C77;
	padding: 5px 0 5px 5px;

	/* border-bottom: 1px solid gray; */
	margin: 10px auto 10px auto; 
	background-color:white;
	color: gray;

}
.articleTitleBox {
	display: flex;
	flex-direction: column;
	margin: 0 0 0 8px;
}
.articleTitle {
	padding-top: 2px;
	font-weight: bold;

}
.articleCreatedAt {
	display: flex;
	flex-direction: column ;
	font-size: 5px;
	margin-right: 10px;
}
</style>