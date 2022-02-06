<template>
	<div>
		<div class="container">
			<BarMenu :titles="
			[
				{title:'Books', href:'/books'},
				{title:'Movies', href:'/movies'},
				{title:'My List', href:'/mylist'},
			]"/>
			<router-view/>
		</div>
	</div>
</template>

<script>
import BarMenu from './components/BarMenu.vue'

export default {
	name: 'App',
	components: {
		BarMenu
	}
}
</script>

<style>
#app {
	font-family: Avenir, Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	text-align: center;
	color: #2c3e50;
}
</style>
