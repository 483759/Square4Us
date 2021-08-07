<template>
  <div id='frame'>
    <header id='frame-header'>
      <slot name='header'>
      </slot>
    </header>
    <section id='frame-section'>
      <slot name='section'>
      </slot>
    </section>
  </div>
</template>

<script>
export default {
  name: 'StudyListFrame'
}
</script>

<style scoped>
  #frame {
    box-sizing: border-box;
    min-height: 600px;
    min-width: 900px;
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    padding: 35px 40px;
    padding-top: 15px !important;
    /* 왼쪽오른쪽 60정도 */
  }

  #frame-header {
    box-sizing: border-box;
    height: 100px;
    display: flex;
    /* box-shadow: 0 0 0 1px #195C77 inset; */

  }
  #frame-section {
    background-color: white;
    flex-basis: 1030px;
    flex-grow: 1;
    box-sizing: border-box;
    border-radius: 5px;
    margin-top: 10px;
  }
</style>