<template>
		<view class="root-padding">
<checkbox  :class="checkbox[0].checked?'checked':''" :checked="checkbox[0].checked?true:false" value="A" ></checkbox>
	<uni-group title="基础用法">
	
				<uni-data-checkbox multiple v-model="momentInfo.momentList" :localdata="momentList">
					
				</uni-data-checkbox>
			</uni-group>
		<!-- 搜索框 -->
		<view class="search">
			<image src="../../static/icon/search.png" class="search-icon"></image>
			<input placeholder="搜索喜欢的内容" placeholder-class="search-input-placeholder" maxlength="20" class="search-input"
			 confirm-type="search" @confirm="searchMe" />
		</view>



<scroll-view :scroll-y="modalName==null" class="page" :class="modalName!=null?'show':''">
			<cu-custom bgColor="bg-gradual-pink" :isBack="true">
				<block slot="backText">返回</block>
				<block slot="content">列表</block>
			</cu-custom>

			<view class="cu-bar bg-white solid-bottom margin-top">
				<view class="action">
					<text class="cuIcon-title text-orange "></text> 消息列表
				</view>
			</view>
			<view class="cu-list menu-avatar">
				<view class="cu-item">
					<view class="cu-avatar round lg" style="background-image:url(https://ossweb-img.qq.com/images/lol/web201310/skin/big10001.jpg);"></view>
					<view class="content">
						<view class="text-grey">凯尔</view>
						<view class="text-gray text-sm flex">
							<view class="text-cut">
								<text class="cuIcon-infofill text-red  margin-right-xs"></text>
								我已天理为凭，踏入这片荒芜，不再受凡人的枷锁遏制。我已天理为凭，踏入这片荒芜，不再受凡人的枷锁遏制。
							</view> </view>
					</view>
					<view class="action">
						<view class="text-grey text-xs">22:20</view>
						<view class="cu-tag round bg-grey sm">5</view>
					</view>
				</view>
				<view class="cu-item">
					<view class="cu-avatar round lg" style="background-image:url(https://ossweb-img.qq.com/images/lol/img/champion/Taric.png);">
						<view class="cu-tag badge">99+</view>
					</view>
					<view class="content">
						<view class="text-grey">
							<view class="text-cut">瓦洛兰之盾-塔里克</view>
							<view class="cu-tag round bg-orange sm">战士</view>
						</view>
						<view class="text-gray text-sm flex">
							<view class="text-cut">
								塔里克是保护者星灵，用超乎寻常的力量守护着符文之地的生命、仁爱以及万物之美。塔里克由于渎职而被放逐，离开了祖国德玛西亚，前去攀登巨神峰寻找救赎，但他找到的却是来自星界的更高层的召唤。现在的塔里克与古代巨神族的神力相融合，以瓦洛兰之盾的身份，永不疲倦地警惕着阴险狡诈的虚空腐化之力。
							</view>
						</view>
					</view>
					<view class="action">
						<view class="text-grey text-xs">22:20</view>
						<view class="cuIcon-notice_forbid_fill text-gray"></view>
					</view>
				</view>
				<view class="cu-item ">
					<view class="cu-avatar radius lg" style="background-image:url(https://ossweb-img.qq.com/images/lol/img/champion/Morgana.png);"></view>
					<view class="content">
						<view class="text-pink"><view class="text-cut">莫甘娜</view></view>
						<view class="text-gray text-sm flex"> <view class="text-cut">凯尔，你被自己的光芒变的盲目！</view></view>
					</view>
					<view class="action">
						<view class="text-grey text-xs">22:20</view>
						<view class="cu-tag round bg-red sm">5</view>
					</view>
				</view>
				<view class="cu-item grayscale">
					<view class="cu-avatar radius lg" style="background-image:url(https://ossweb-img.qq.com/images/lol/web201310/skin/big81007.jpg);"></view>
					<view class="content">
						<view><view class="text-cut">伊泽瑞尔</view>
							<view class="cu-tag round bg-orange sm">断开连接...</view>
						</view>
						<view class="text-gray text-sm flex"> <view class="text-cut"> 等我回来一个打十个</view></view>
					</view>
					<view class="action">
						<view class="text-grey text-xs">22:20</view>
						<view class="cu-tag round bg-red sm">5</view>
					</view>
				</view>
				<view class="cu-item cur">
					<view class="cu-avatar radius lg" style="background-image:url(https://ossweb-img.qq.com/images/lol/web201310/skin/big81020.jpg);">
						<view class="cu-tag badge"></view>
					</view>
					<view class="content">
						<view>
							<view class="text-cut">瓦罗兰大陆-睡衣守护者-新手保护营</view>
							<view class="cu-tag round bg-orange sm">6人</view>
						</view>
						<view class="text-gray text-sm flex">
							<view class="text-cut"> 伊泽瑞尔：<text class="cuIcon-locationfill text-orange margin-right-xs"></text> 传送中...</view></view>
					</view>
					<view class="action">
						<view class="text-grey text-xs">22:20</view>
						<view class="cuIcon-notice_forbid_fill text-gray"></view>
					</view>
				</view>
			</view>
			<view class="cu-bar bg-white solid-bottom margin-top">
				<view class="action">
					<text class="cuIcon-title text-orange "></text> 列表左滑
				</view>
			</view>
			

		</scroll-view>








		<!-- 轮播图 -->
		<swiper :indicator-dots="true" :autoplay="true" class="swiper" circular="true">
			<swiper-item v-for="swiper in swiperList">
				<navigator open-type="navigate" :url="'../video?trailerId=' + swiper.movieId">
					<image :src="swiper.coverUrl" mode="aspectFill" class="swiper-image"></image>
				</navigator>
			</swiper-item>
		</swiper>

		<!-- 板块 -->
		<view class="block">
			<view class="block-title">
				最近热门
			</view>
			<view class="block-item-list">
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
			</view>
		</view>
		<view class="block">
			<view class="block-title">
				最新发布
			</view>
			<view class="block-item-list">
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
			</view>
		</view>
	</view>

</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';

	export default {
		data() {
			return {
				formData: {
					
					hobby: [],
					
				},
				swiperList: [],
				hobby: [
					{
						text: '足球1',
						value: '足球1'
					},
					{
						text: '篮球',
						value: 1
					},
					{
						text: '游泳',
						value: 2
					}
				],
			}
		},

		async onLoad() {
			// 判断是否登录
			let currentPage = this.getCurrentPage(getCurrentPages());
			let [checkLoginTokenData, checkLoginTokenDataError] = await httpUtils.postJson("/login/checkLoginToken", {},
				currentPage);
			if (checkLoginTokenDataError === "请重新登录") {
				currentPage = encodeURIComponent(currentPage);
				uni.redirectTo({
					url: `/pages/login/login?fromPage=${currentPage}`
				});
				return;
			}

			// 轮播图
			let [data] = await httpUtils.postJson("/index/queryIndexSwiper");
			this.swiperList = data.body;
		},

		async onPullDownRefresh() {
			this.swiperList = [];
			// 轮播图
			let [data] = await httpUtils.postJson("/index/queryIndexSwiper");
			this.swiperList = data.body;
			uni.stopPullDownRefresh();
		},

		async onReachBottom() {
			console.log("触底");
		},

		async onShareAppMessage(res) {
			await httpUtils.postJson("/share/share", {
				pageType: "INDEX"
			});

			let loginUser = uni.getStorageSync("loginUser");
			return {
				title: getApp().globalData.appName,
				path: `/pages/index/index?shareUserId=${loginUser.userId}`
			}
		},

		methods: {
			login() {
				console.log("login");
			},
			submitForm(hobby) {
				 console.log(this.formData.hobby);
				// this.$refs[form]
				// 	.submit()
				// 	.then(res => {
				// 		console.log('表单的值：', res)
				// 		uni.showToast({
				// 			title: '验证成功'
				// 		})
				// 	})
				// 	.catch(errors => {
				// 		console.error('验证失败：', errors)
				// 	})
			},

		}
	}
</script>

<style>
	/* 搜索框 */
	.search {
		display: flex;
		flex-direction: row;
		margin: 8rpx 0 12rpx 0;
	}

	.search-icon {
		width: 46rpx;
		height: 46rpx;
		margin-left: -8rpx;
	}

	.search-input {
		flex: 1;
		padding-left: 20rpx;
	}

	.search-input:focus {
		padding-left: 20rpx;
	}

	/* 轮播图 */
	.swiper {
		width: 690rpx;
		height: 388rpx;
		border-radius: 6rpx;
		margin-bottom: 32rpx;
		overflow: hidden;
	}

	.swiper-image {
		width: 690rpx;
		height: 388rpx;
	}

	/* 板块 */
	.block {
		margin-bottom: 22rpx;
	}

	.block-title {
		font-size: 34rpx;
		font-weight: bold;
		margin: 0 0 32rpx 0;
	}

	.block-item-list {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		flex-wrap: wrap;
	}

	.block-item {
		width: 337rpx;
		border-radius: 2rpx;
		margin: 0 0 22rpx 0;
	}

	.block-item-image {
		width: 337rpx;
		height: 190rpx;
		border-radius: 2rpx;
	}

	.block-item-title {
		font-size: 29rpx;
		color: #151516;
		line-height: 52rpx;
	}

	.block-item-desc {
		font-size: 24rpx;
		color: #9e9ca5;
	}
</style>
