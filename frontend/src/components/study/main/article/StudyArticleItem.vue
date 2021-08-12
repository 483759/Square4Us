<template>
<li>  
    <div class='article-title' style="width=400px">{{ article.title }} </div> 
    <div class='article-author' style="width=400px">{{ article.member.nickname }} </div> 
    <div class='article-date' style="width=400px">{{ dateFormatter(article.createdDate) }} </div> 
    
  <!-- <button class='read-button' @click="$emit('onEnter', article.id)">입장</button> -->
</li>
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
</style>