<template>
  <div class='tiptap-wrapper'> <!-- 내가 넣은 클래스 -->
  <input type="text" v-model='title' id='tiptap-title'>
    
    <p v-if="editor" class='tiptap-buttons'> <!-- 내가 넣은 클래스 -->
      <button @click="editor.chain().focus().toggleBold().run()" :class="{ 'is-active': editor.isActive('bold') }">
        bold
      </button>
      <button @click="editor.chain().focus().toggleItalic().run()" :class="{ 'is-active': editor.isActive('italic') }">
        italic
      </button>
      <button @click="editor.chain().focus().toggleStrike().run()" :class="{ 'is-active': editor.isActive('strike') }">
        strike
      </button>
      <button @click="editor.chain().focus().toggleCode().run()" :class="{ 'is-active': editor.isActive('code') }">
        code
      </button>
      <button @click="editor.chain().focus().unsetAllMarks().run()">
        clear marks
      </button>
      <button @click="editor.chain().focus().clearNodes().run()">
        clear nodes
      </button>
      <button @click="editor.chain().focus().setParagraph().run()" :class="{ 'is-active': editor.isActive('paragraph') }">
        paragraph
      </button>
      <button @click="editor.chain().focus().toggleHeading({ level: 1 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 1 }) }">
        h1
      </button>
      <button @click="editor.chain().focus().toggleHeading({ level: 2 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }">
        h2
      </button>
      <button @click="editor.chain().focus().toggleHeading({ level: 3 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 3 }) }">
        h3
      </button>
      <button @click="editor.chain().focus().toggleHeading({ level: 4 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 4 }) }">
        h4
      </button>
      <button @click="editor.chain().focus().toggleHeading({ level: 5 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 5 }) }">
        h5
      </button>
      <button @click="editor.chain().focus().toggleHeading({ level: 6 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 6 }) }">
        h6
      </button>
      <button @click="editor.chain().focus().toggleBulletList().run()" :class="{ 'is-active': editor.isActive('bulletList') }">
        bullet list
      </button>
      <button @click="editor.chain().focus().toggleOrderedList().run()" :class="{ 'is-active': editor.isActive('orderedList') }">
        ordered list
      </button>
      <button @click="editor.chain().focus().toggleCodeBlock().run()" :class="{ 'is-active': editor.isActive('codeBlock') }">
        code block
      </button>
      <button @click="editor.chain().focus().toggleBlockquote().run()" :class="{ 'is-active': editor.isActive('blockquote') }">
        blockquote
      </button>

    <button
      @click="editor.chain().focus().toggleHighlight().run()"
      :class="{ 'is-active': editor.isActive('highlight') }"
    >
      highlight
    </button>




      <button @click="editor.chain().focus().setHorizontalRule().run()">
        horizontal rule
      </button>
      <button @click="editor.chain().focus().setHardBreak().run()">
        hard break
      </button>
      <button @click="editor.chain().focus().undo().run()">
        undo
      </button>
      <button @click="editor.chain().focus().redo().run()">
        redo
      </button>
    </p>

    <BubbleMenu
      class="bubble-menu"
      :tippy-options="{ duration: 100 }"
      :editor="editor"
      v-if="editor"
    >
      <button @click="editor.chain().focus().toggleBold().run()" :class="{ 'is-active': editor.isActive('bold') }">
        Bold
      </button>
      <button @click="editor.chain().focus().toggleItalic().run()" :class="{ 'is-active': editor.isActive('italic') }">
        Italic
      </button>
      <button @click="editor.chain().focus().toggleStrike().run()" :class="{ 'is-active': editor.isActive('strike') }">
        Strike
      </button>
      <button @click="editor.chain().focus().toggleHighlight().run()" :class="{ 'is-active': editor.isActive('highlight') }" >
        highlight
      </button>
    </BubbleMenu>
  <EditorContent :editor="editor" />
    <p class='article-buttons'>
      <button @click='save'>저장</button>
      <button>뒤로가기</button>
    </p>
  </div>
</template>

<script>
import { Editor, EditorContent, BubbleMenu, } from '@tiptap/vue-3'
import StarterKit from '@tiptap/starter-kit'
import Highlight from '@tiptap/extension-highlight'
import Typography from '@tiptap/extension-typography'

export default {
  components: {
    EditorContent,
    BubbleMenu,
  },
  props: {
    modelValue: {
      type: String,
      default: '',
    },
  },
  // emits: ["updateModelValue"],
  data() {
    return {
      editor: null,
      title: ''
    }
  },
  methods: {
    // 실제 store 접근은 StudyArticle.vue에서 이루어진다
    save : function () {
      // this.title과 this.modelValue 를 저장하면 된다.
      const data = {
        req: {
          "category": "15",
          "title": this.title,
          "content": this.modelValue
        },
        files: [],
      }
      this.$emit('saveArticle', data)
    }
  },
  watch: {
    modelValue(value) {
      // console.log(this.editor.getHTML());
      const isSame = this.editor.getHTML() === value
      if (isSame) {
        return
      }
      this.editor.commands.setContent(this.modelValue, false)
    },
  },
  mounted() {
    this.editor = new Editor({
      content: this.modelValue,
      extensions: [
        StarterKit,
        Highlight,
        Typography,
        
      ],
      onUpdate: () => {
        this.$emit('updateModelValue', this.editor.getHTML())
      },
      // editorProps: {
      //   attributes: {
      //     class:
      //       "prose prose-sm sm:prose lg:prose-lg xl:prose-2xl m-5 focus:outline-none"
      //   }
      // },
    })
  },

  beforeUnmount() {
    this.editor.destroy()
  },
}
</script>



<style >
.tiptap-wrapper {
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  padding: 0 5%;
}
.article-buttons {
  height: 50px;
}
.tiptap-wrapper > div {
  height: 55vh; 
  flex-grow: 1;
}

#tiptap-title {
  border: 2px solid grey;
  border-radius: 5px;
  height: 20px;
  font-size: 1.2rem;
  font-weight: bolder;
  color: rgb(80, 80, 80);
}

/* Basic editor styles */
.ProseMirror {
  border-radius: 5px;
  border: 2px solid grey;
  text-align: start;
  padding-left: 10px;
  height: 100%;
  
}
.ProseMirror p{
}

.ProseMirror ul{
  list-style-type: disc;
  padding-left: 20px;
}
/* .ProseMirror {
  > * + * {
    margin-top: 0.75em;
  }

  ul,
  ol {
    padding: 0 1rem;
  }

  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    line-height: 1.1;
  }

  code {
    background-color: rgba(#616161, 0.1);
    color: #616161;
  }

  pre {
    background: #0D0D0D;
    color: #FFF;
    font-family: 'JetBrainsMono', monospace;
    padding: 0.75rem 1rem;
    border-radius: 0.5rem;

    code {
      color: inherit;
      padding: 0;
      background: none;
      font-size: 0.8rem;
    }
  }

  img {
    max-width: 100%;
    height: auto;
  }

  blockquote {
    padding-left: 1rem;
    border-left: 2px solid rgba(#0D0D0D, 0.1);
  }

  hr {
    border: none;
    border-top: 2px solid rgba(#0D0D0D, 0.1);
    margin: 2rem 0;
  }
} */
</style>