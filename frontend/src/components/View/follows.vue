<template>
<div>
    <div class="listLeft">
        <ul aria-label="Following:">
            <li>Left Item 1</li>
            <li>Left Item 2</li>
            <li>Left Item 3</li>
            <li>Left Item 4</li>
            <li>Left Item 5</li>
        </ul>
    </div>
    <div class="listRigh">
        <ul aria-label="Followers:">
            <li>Right Item 1</li>
            <li>Right Item 2</li>
            <li>Right Item 3</li>
            <li>Right Item 4</li>
            <li>Right Item 5</li>
        </ul>
    </div>
</div>
</template>

<style scoped>
.listRight {
    float: right;
    width: 50%;
}

.listLeft {
    float: left;
    width: 50%;
}

ul:before{
    content:attr(aria-label);
    font-size:120%;
    font-weight:bold;
    margin-left:-15px;
}
</style>