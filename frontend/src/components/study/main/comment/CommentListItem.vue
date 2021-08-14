<template lang="">
  <div>
    <p>{{comment.content}}</p>
  </div>
</template>
<script>
export default {
  name: 'CommentListItem',
  props: {
    comment: {
      type: Object,
      required: true
    }
  },
  setup(props) {
    return {
      props
    }
  }
}
</script>
<style lang="">
  
</style>