<template>
    <nav class="navbar" role="navigation" aria-label="main navigation">
  <div class="navbar-brand">
    <a class="navbar-item" href="https://bulma.io">
    <img src="https://bulma.io/images/bulma-logo.png" width="112" height="28">
    </a>

    <a role="button" class="navbar-burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
      <span aria-hidden="true"></span>
      <span aria-hidden="true"></span>
      <span aria-hidden="true"></span>
    </a>
  </div>

  <div id="navbarBasicExample" class="navbar-menu">
    <div class="navbar-start">
      <router-link class="navbar-item" v-for="t in titles" :key="t.title"
            :to="t.href">
        {{t.title}}
      </router-link>
      </div>
    </div>
    </nav>
</template>


<script>

export default
{
    name:"BarMenu",
    props:
    {
        titles: Array
    }
}
</script>

<style>
.navbar
{
    box-shadow: 10px -25px 50px;
    margin-bottom: 50px;
}
</style>