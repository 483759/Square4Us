<template>
  <Tiptap :modelValue='state.modelValue' @updateModelValue='updateModelValue' />
</template>

<script>
import { reactive } from '@vue/reactivity'
import Tiptap from '@/components/study/main/article/Tiptap.vue'

export default {
  name: 'TiptapControl',
  components: {
    Tiptap
  },
  setup() {
    // 실제 컨텐츠 값
    const state = reactive({
      modelValue : '' // 실제 이 값만 잘 기억하면 써먹을 수 있다.
    })
    // 컨텐츠 업데이트 값
    const updateModelValue = (data)=>{
      state.modelValue=data
    }



    return {
      state,
      updateModelValue,
    }
  }
}
</script>
