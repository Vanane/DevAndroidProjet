<template>
    <div class="content">
        <p class="title is-1">Welcome</p>
        <router-link :to="{ name: 'BookList' }">Book List</router-link><br>    
        <router-link :to="{ name: 'List' }">My Watchlist</router-link>
        <router-link :to="{name: 'ScanBook' }">Scan a Book</router-link>
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