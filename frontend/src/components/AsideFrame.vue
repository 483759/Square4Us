<template>
  <div id='frame'>
    <aside id='frame-aside'>
      <section id='frame-aside-header'>
        <slot name='aside-header'>
        </slot>
      </section>
      <section id='frame-aside-body'>
        <slot name='aside-body'>
        </slot>
      </section>
    </aside>
    <section id='frame-section'>
      <slot name='section'>
      </slot>
    </section>
  </div>
</template>

<script>
export default {
  name: 'AsideFrame'
}
</script>

<style scoped>
  #frame {
    box-sizing: border-box;
    min-height: 600px;
    min-width: 900px;
    flex-grow: 1;
    display: flex;
    justify-content: space-between;
    padding: 35px 40px;
    /* 왼쪽오른쪽 60정도 */
  }

  #frame-aside {
    /* flex-basis: 265px; */
    width: 230px;
    /* flex-shrink: 1; */
    /* background-color: rgb(255, 255, 255); */
    box-sizing: border-box;
    margin: 0 20px 0 0;

    display: flex;
    flex-direction: column;
    
  }

  #frame-aside-header {
    flex-basis: 150px;
    width: 230px;
    background-color:  white;
    margin-bottom: 20px;
    border-radius: 5px;
  }
  #frame-aside-body {
    box-sizing: border-box;
    /* 여백이 안생기려면 box-shadow를 써야한다 */
    box-shadow: 0 0 0 1px #195C77 inset; 
    width: 230px;
    flex-grow: 1;
    background-color:  white;
    border-radius: 5px;
    /* border: 2px solid #195C77; */

    display: flex;
    flex-direction: column;
    justify-content: flex-start;
  }

  #frame-section {
    flex-basis: 800px;
    flex-grow: 1;
    background-color: rgb(255, 255, 255);
    box-sizing: border-box;
    margin: 0 0 0 20px;
    border-radius: 5px;

    display: flex;
    flex-direction: column;
  }
</style>