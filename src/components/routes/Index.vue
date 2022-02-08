<template>
    <div class="content">
        <p class="title is-1">Welcome</p>
        <router-link :to="{ name: 'BookList' }">Book List</router-link><br>
        <a>Movie List (WIP)</a><br>
        <router-link :to="{ name: 'List' }">My Watchlist</router-link>
    </div>
</template>

<script>
export default {
    name: "Index",

    beforeMount() {
        this.init();
    }
}
</script>

<style>

</style>