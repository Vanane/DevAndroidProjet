<template>
	<div>
		<div class="container">
			<BarMenu :titles="
			[
				{title:'Home', href:'/'},
				{title:'Search Books', href:'/books'},
				{title:'My List', href:'/mylist'},
				{title:'Scan a Book', href:'/scan'},
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
