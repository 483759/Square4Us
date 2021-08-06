export default {
    setModalSize(idValue, width, height, top, left) {
        const modal = document.getElementById(idValue)
        modal.style.width = width
        modal.style.height = height
        modal.style.top = top
        modal.style.left = left
    }
}