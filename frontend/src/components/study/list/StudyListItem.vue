<template>
  <li class='card'>
      <header class='card-image'>
        <img src="/card-sample.jpg" alt="카드">
      </header>
      <article class='card-article'>스터디 이름, 설명</article>
  </li>
</template>

<script>
export default {
  name: 'StudyListItem',
}
</script>

<style>
  .card {
    height: auto;
    margin: 10px 10px;
    
  }

  .card-image {
    display: block;
    height: 70%;
    background-color: beige;
  }

  .card-image > img {
    width: 100%;
    height: 100%;
  }
  .card-article {
    height: 30%;
    background-color: bisque;
    font-weight:bolder;
    font-size: 1.0rem;
    
    
  }

</style>