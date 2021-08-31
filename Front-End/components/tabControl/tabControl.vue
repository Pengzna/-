<template>
	<view class="tab-control">
		<view v-for="(item,index) in titles" class="tab-control-item" :class="{active:index===currentIndex}" @click="itemClick(index)">
			<span>{{item}}</span>
		</view>
	</view>
</template>

<script>
	export default {
		name:"tabControl",
		props:{
			titles:{
				type:Array,
					
				default(){
					return []
				}
			}
		},
		data() {
			return{
				currentIndex:0
			};
		},
		methods:{
			itemClick(index){
				this.currentIndex=index;
			}
		}
	}
</script>

<style scoped>
	.tab-control{
		display: flex;
		text-align: center;
		font-size: 15px;
		background-color: #fff;
	}
	
	.tab-control-item{
		flex:1;
		height: 40px;
		line-height: 40px;
	}
	
	.tab-control-item span {
		padding: 5px;
	}
	
	.active{
		font-weight:700;
		color:#FCC23D;
	}
		
	.active span{
		border-bottom: 3px solid #FCC23D;
	}
</style>
