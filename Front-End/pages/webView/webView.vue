<template>
	<view>
		 <web-view  :src="url"></web-view>
	</view>
</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';
	export default {
		data() {
			return {
				id:"",
				url:"",
			}
		},
		 onLoad(options){//内置浏览器，用来打开超链接
			this.url = decodeURIComponent(options.url);
			console.log(this.url)
			// if(this.isNotEmpty(val.title)){
			// 				this.setTitle(val.title);
			// 			}
			// this.id = options.id;
			// console.log(options.id)
			// let [data] = await httpUtils.postJson("/activity/queryOneActivity", {
			// 	id: 1,
				
			// });
			// console.log(data.body.posterImage)
			// this.url=data.body.sourceUrl;
		},
		methods: {
			isNotEmpty(obj) {
							if (typeof obj == undefined || obj == null || obj == "" || obj == "undefined" || obj.length == 0) {
								return false;
							} else {
								return true;
							}
						},
						// 设置title
						setTitle(title) {
							uni.setNavigationBarTitle({
								title: title
							})
						},
			
		}
	}
</script>

<style>

</style>
