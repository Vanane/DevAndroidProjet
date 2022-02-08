<template>
		<nav class="navbar" role="navigation" aria-label="main navigation">
	<div class="navbar-brand">		
		<a @click="this.onBurgerClick" ref="burgerMenu" role="button" class="navbar-burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
			<span aria-hidden="true"></span>
			<span aria-hidden="true"></span>
			<span aria-hidden="true"></span>
		</a>
	</div>

	<div ref="navbarMenu" class="navbar-menu">
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
		data() {
			return {
				burgerToggle: false
			}
		},		
		props:
		{
			titles: Array
		},

		methods: {
			onBurgerClick() {
				this.burgerToggle = !this.burgerToggle;
				if(this.burgerToggle)
					this.openBurger();
				else
					this.closeBurger();
			},

			openBurger() {
				this.$refs.burgerMenu.classList.add("is-active");
				this.$refs.navbarMenu.classList.add("is-active");
			},

			closeBurger() {
				this.$refs.burgerMenu.classList.remove("is-active");
				this.$refs.navbarMenu.classList.remove("is-active");
			}
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