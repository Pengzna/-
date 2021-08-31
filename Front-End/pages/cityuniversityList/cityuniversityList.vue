<template>
	<view>
		<city-select @cityClick="cityClick" :formatName="formatName" :obtainCitys="obtainCitys" :isSearch="true" ref="citys"></city-select>
	</view>
</template>


<script>
	import citySelect from '@/components/city-select/city-select.vue'
	import httpUtils from '../../common/util/httpUtils.js';

	export default {
		data() {
			return {
				pageNo: 1,
				pageSize: 20,
				formatName: 'name',
				activeCity: {},
				obtainCitys: []
			}
		},
		components: {
			citySelect
		},

		async onLoad() {
			//修改需要构建索引参数的名称
			this.formatName = 'name'

			// 加载数据
			uni.showLoading({
				title: '数据加载中...'
			});
			let [cityData] = await httpUtils.postJson("/setting/queryUniversity", {});
			this.obtainCitys = cityData.body;
			uni.hideLoading();
		},
		methods: {
			async cityClick(item) {
				
				uni.reLaunch({
				    url: "/pages/universityList/universityList?userId="+item.id
				});
				// await httpUtils.postJson("/square/querySomeMoment", {
				// 	pageNo: this.pageNo,
				// 	pageSize: this.pageSize,
				// 	userId: item.id
				// });
				// uni.$emit('update', {
				// 	userid:item.id
				// });
				// uni.setStorage({
    //             key:'swiper_info',
    //             data:item.id,
    //             success:()=>{
    //                 console.log('存入成功')
    //             }
    //         });
				// uni.navigateBack({
				// 	//delta: 1
				// 	url: "/pages/universityList/universityList"
				// });
				
			//}

				// const delta = 1 // 返回的页面数  
				// uni.navigateBack({
				// 	delta: 1,
				// 	success: () => {
				// 		const pages = getCurrentPages() // 获取当前页面栈数组，第一个元素为首页，最后一个元素为当前页面  
				// 		 let page = pages[Math.max(pages.length - 1 - delta-1, 0)] // 要返回到的页面，超过页面栈，则为首页  
				// 		  console.log(page.options)
						  
				// 		  page.onLoad(page.options)
						  
				// 		   //page.loadMomentInfo();
						  
						  
				// 	}
						
				// 	// url: "../universityList/universityList"
				// });
				
			},
			
			// async onHide(){
			// 	uni.redirectTo({
			// 		//delta: 1
			// 		url: "/pages/universityList/universityList"
			// 	});
			// }
		}
	}
</script>


<style></style>
