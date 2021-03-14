<template>
        <div class="descriptionarea">
                <textarea type="text" v-model="newMessage" placeholder="Write your message" rows="10" cols="40"/>
                <span class="buttonfortxtarea">
                    <button @click="postToBackend()" class="button button1">Send</button>
                </span>
        </div>
</template>

<style scoped>
textarea {
    resize: none;
}
</style>