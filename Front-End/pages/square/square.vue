<template>
	
	<view class="square-root">
		<view class="open-popup" @click="openPopup">
			<view class="open-popup-horizontal">
			</view>
			<view class="open-popup-vertical">
			</view>
		</view>
		<!-- 文字滚动 -->
		<uni-notice-bar speed="50" color="#ff0000" scrollable="true" single="true" :text="'🍑大喇叭：'+noticeInfo.description"></uni-notice-bar>
		
		    <!-- <hm-notice></hm-notice> -->
	
		<!-- <view class="tab-nav-main-wrapper">
			<view class="tab-nav-main">
				<view class="tab-nav-list" ref="tabNavList">
					<view class="tab-nav-item" :class="curTabIndex==0 ? 'tab-nav-item-active' : ''" :data-index="0" @click="clikeTabNav">
						动态
					</view>
					<view class="tab-nav-item" :class="curTabIndex==1 ? 'tab-nav-item-active' : ''" :data-index="1" @click="clikeTabNav">
						视频
					</view>
					<view class="tab-nav-item" :class="curTabIndex==2 ? 'tab-nav-item-active' : ''" :data-index="2" @click="clikeTabNav">
						相册
					</view>
				</view>
				<view class="tab-nav-underline tab-nav-underline-animation" :style="{left: tabNavUnderlineLeft + 'rpx', width: tabNavUnderlineWidth + 'rpx'}">
				</view>
			</view>
		</view> -->
		<!-- <view class="line"></view> -->
<view>
<moment v-for="(moment) in momentInfo.momentList" :moment="moment" momentPageType="LIST" v-on:updateMomentLikeCount="updateMomentLikeCount($event,moment)"
				 v-on:updateMomentHasLiked="updateMomentHasLiked($event,moment)">
				</moment>
				
				
				
				</view>
		<!-- <swiper class="tab-content" :duration="300" :current="curTabIndex" @change="swiperChange" acceleration="false">
			
			<swiper-item class="tab-content-item">
	
				<moment v-for="(moment) in momentInfo.momentList" :moment="moment" momentPageType="LIST" v-on:updateMomentLikeCount="updateMomentLikeCount($event,moment)"
				 v-on:updateMomentHasLiked="updateMomentHasLiked($event,moment)">
				</moment>
				
			</swiper-item>
			
			<swiper-item class="tab-content-item">
				<view class="page-body">

				            <view class="page-section page-section-gap" style="text-align: center;">
								哈哈，你发现了小彩蛋哦~~送你一首歌
								<image src="/static/icon/doge.png" ></image>
				                <audio style="text-align: left" :src="current.src" :poster="current.poster" :name="current.name" :author="current.author" :action="audioAction" controls></audio>
				            </view>
				        </view>
							</swiper-item> -->
			<!-- <swiper-item class="tab-content-item">
				<videoItem v-for="(moment) in videoInfo.momentList" :moment="moment" v-on:updateMomentLikeCount="updateMomentLikeCount($event,moment)"
				 v-on:updateMomentHasLiked="updateMomentHasLiked($event,moment)"></videoItem>
			</swiper-item> -->
			<!-- <swiper-item class="tab-content-item">
				<imageItem v-for="(imageList,date) in imageInfo.imageMap" :date="date" :imageList="imageList"></imageItem>
			</swiper-item> -->
			
		<!-- </swiper> -->

		
		
		<uni-popup ref="popup" type="bottom" >
			<view class="popup">
				<view class="popup-publish">
					<view class="popup-publish-image-wrapper" @click="toPublishMoment(countInfo.gender)">
						<image class="popup-publish-image-moment" src="../../static/icon/publish-moment.png" mode="aspectFill"></image>
					</view>
					<view>发动态</view>
				</view>
				<!-- 这里将提交动态改为发布活动，页面跳转至activityDeliver -->
				<view class="popup-publish">
					<view class="popup-publish-image-wrapper" @click="toPublishVideo">
						<image class="popup-publish-image-video" src="../../static/icon/sub-moment.png" mode="aspectFill"></image>
					</view>
					<view>发布活动</view>
				</view>
				<!-- <view class="popup-publish-image-wrapper" @click="toPublishMoment">
					<image class="popup-publish-image-moment" src="../../static/icon/publish-moment.png" mode="aspectFill"></image>
				</view> -->
			</view>
		</uni-popup>
	</view>
</template>

<script>
	import uniPopup from '@/components/uni-popup/uni-popup.vue';
	import httpUtils from '../../common/util/httpUtils.js';
	import moment from '../../components/moment/moment.vue';
	import videoItem from '../../components/videoItem/videoItem.vue';
	import imageItem from '../../components/imageItem/imageItem.vue';

	export default {
		components: {
			moment,
			videoItem,
			imageItem,
			uniPopup,
			
		},

		data() {
			return {
				countInfo:{},
				noticeInfo: {},
				current: {
				                poster: 'https://bjetxgzv.cdn.bspapp.com/VKCEYUGU-uni-app-doc/7fbf26a0-4f4a-11eb-b680-7980c8a877b8.png',
				                name: '致爱丽丝',
				                author: '要元气满满哦~',
				                src: 'https://bjetxgzv.cdn.bspapp.com/VKCEYUGU-hello-uniapp/2cc220e0-c27a-11ea-9dfb-6da8e309e0d8.mp3',
				            },
				            audioAction: {
				                method: 'pause'
				            },
				// 当前tab索引
				curTabIndex: 0,
				// 下划线left
				tabNavUnderlineLeft: 4,
				// 下划线width
				tabNavUnderlineWidth: 100,

// 动态数据
				activityInfo: {
					pageNo: 1,
					pageSize: 20,
					activityList: []
				},
				
				// 动态数据
				momentInfo: {
					pageNo: 1,
					pageSize: 20,
					momentList: []
				},
				// 视频数据
				videoInfo: {
					pageNo: 1,
					pageSize: 20,
					momentList: []
				},
				// 图片数据
				imageInfo: {
					pageNo: 1,
					pageSize: 20,
					imageMap: {}
				}
			}
		},

		async onLoad() {
			console.log(this.getLoginUser());
			await this.loadnotice();
			await this.loadCountInfo();
			// if(this.countInfo==null){
				
			// 	uni.clearStorage();
			// 	uni.reLaunch({
			// 		url: "/pages/login/login"
			// 	});
			// }
			// 加载moment数据
			await this.loadMomentInfo();
			// 加载video数据
			//await this.loadVideoInfo();
			// 加载image数据
			//await this.loadImageInfo();

			uni.$on('updateMomentHasFollowed', event => {
				console.log("trig",event)
				for (let i in this.momentInfo.momentList) {
					let moment = this.momentInfo.momentList[i];
					if (moment.id === event.id) {
						moment.hasFollowed = event.hasFollowed;
						break;
					}
				}
				for (let i in this.videoInfo.momentList) {
					let moment = this.videoInfo.momentList[i];
					if (moment.id === event.id) {
						moment.hasFollowed = event.hasFollowed;
						break;
					}
				}
			});
		},

		async onPullDownRefresh() {
			await this.loadCountInfo();
			
			if(this.countInfo.commentUnread+this.countInfo.atUnread>0){
				this.countInfo.commentUnread=this.countInfo.atUnread+this.countInfo.commentUnread+" ";
				uni.setTabBarBadge({
				  index: 2,
				  text: this.countInfo.commentUnread
				})
			}
			if (this.curTabIndex === 0) {

				this.momentInfo = {
					pageNo: 1,
					pageSize: 20,
					momentList: []
				};
				await this.loadMomentInfo();

			} else if (this.curTabIndex === 1) {

				this.videoInfo = {
					pageNo: 1,
					pageSize: 20,
					momentList: []
				};
				await this.loadVideoInfo();
			} else {
				this.imageInfo = {
					pageNo: 1,
					pageSize: 20,
					imageMap: {}
				};
				await this.loadImageInfo();
			}
			uni.stopPullDownRefresh();
		},

		async onReachBottom() {
			console.log("触底");
			if (this.curTabIndex === 0) {
				await this.loadMomentInfo();
			} else if (this.curTabIndex === 1) {
				await this.loadVideoInfo();
			} else {
				await this.loadImageInfo();
			}
		},

		async onShareAppMessage(res) {
			await httpUtils.postJson("/share/share", {
				pageType: "SQUARE"
			});

			let loginUser = uni.getStorageSync("loginUser");
			return {
				title: getApp().globalData.appName,
				path: `/pages/square/square?shareUserId=${loginUser.userId}`
			}
		},

		methods: {
			async loadnotice(){
				let [noticeInfoData] = await httpUtils.postJson("/user/getNoticeInfo", {
					status:1
				});
				this.noticeInfo = noticeInfoData.body;
				console.log(this.noticeInfo)
			},
			// 加载个数数据
			async loadCountInfo() {
				let [countData] = await httpUtils.postJson("/user/getUserInfo", {});
				this.countInfo = countData.body;
				console.log(this.countInfo);
			},
			// 界面相关函数
			// 点击tab导航
			clikeTabNav(e) {
				let newIndex = e.target.dataset.index;
				this.updateCurTabIndex(newIndex);
			},

			// 左右滑屏
			swiperChange(e) {
				console.log("swiperChange：" + e.target.source);
				if (e.target.source !== 'touch') {
					return;
				}

				let newIndex = e.target.current;
				newIndex = newIndex > this.curTabIndex ? this.curTabIndex + 1 : this.curTabIndex - 1;
				this.updateCurTabIndex(newIndex);
			},

			updateCurTabIndex(newIndex) {
				newIndex = parseInt(newIndex);
				if (newIndex === this.curTabIndex) {
					return;
				}
				this.tabNavUnderlineLeft += (116 * (newIndex - this.curTabIndex));
				this.curTabIndex = newIndex;
			},

			// 事件相关函数
			// 打开弹层
			openPopup() {
				this.$refs.popup.open();
			},

			// 去发动态
			toPublishMoment(e) {
				
				// if(this.countInfo==null||this.countInfo.gender==-1){
				// 	uni.clearStorage();
				// 	uni.reLaunch({
				// 		url: "/pages/login/login"
				// 	});
				// }
				uni.navigateTo({
					url: `../publishMoment/publishMoment?publishType=MOMENT&gender=${e}`
				});
			},

			// 去提交给管理员发布
			// 改成前往发布活动页
			toPublishVideo() {
				// if(this.countInfo==null||this.countInfo.gender==-1){
				// 	uni.clearStorage();
				// 	uni.reLaunch({
				// 		url: "/pages/login/login"
				// 	});
				// }
				// uni.navigateTo({
				// 	url: `../submitMoment/submitMoment?publishType=MOMENT`
				// });
				uni.navigateTo({
					url: `../activityDeliver/activityDeliver?publishType=MOMENT`
				});
				// uni.chooseVideo({
				// 	count: 1,
				// 	compressed: false,
				// 	sourceType: ['album'],
				// 	success: (res) => {
				// 		console.log(res);
				// 		if (res.size > 1024 * 1024 * 1024) {
				// 			uni.showToast({
				// 				title: "视频最大为1G",
				// 				duration: 2000
				// 			});
				// 			return;
				// 		}

				// 		uni.navigateTo({
				// 			url: `../publishMoment/publishMoment?publishType=VIDEO&tempVideoPath=${res.tempFilePath}&videoOriginalTitle=${res.name}&videoWidth=${res.width}&videoHeight=${res.height}`
				// 		});
				// 	}
				// })
			},

			updateMomentLikeCount(event, moment) {
				moment.likeCount = event;
			},

			updateMomentHasLiked(event, moment) {
				moment.hasLiked = event;
			},

			// 数据相关函数
			// 加载moment数据
			async loadMomentInfo() {
				let [data] = await httpUtils.postJson("/square/querySquareMoment", {
					pageNo: this.momentInfo.pageNo,
					pageSize: this.momentInfo.pageSize,
					
				});

				this.momentInfo.momentList = this.momentInfo.momentList.concat(data.body);
				this.momentInfo.pageNo++;
			},

			// 加载video数据
			async loadVideoInfo() {
				let [data] = await httpUtils.postJson("/square/querySquareMoment", {
					pageNo: this.videoInfo.pageNo,
					pageSize: this.videoInfo.pageSize,
					momentType: 'VIDEO',
					repostMomentId: 0
				});
				
				this.videoInfo.momentList = this.videoInfo.momentList.concat(data.body);
				this.videoInfo.pageNo++;
			},

			// 加载image数据
			async loadImageInfo() {
				let [data] = await httpUtils.postJson("/square/querySquareImage", {
					pageNo: this.imageInfo.pageNo,
					pageSize: this.imageInfo.pageSize
				});
				let dateToImageList = data.body;
				for (let date in dateToImageList) {
					if (!this.imageInfo.imageMap[date]) {
						this.imageInfo.imageMap[date] = [];
					}
					this.imageInfo.imageMap[date] = this.imageInfo.imageMap[date].concat(dateToImageList[date]);
				}
				this.imageInfo.pageNo++;
				console.log(this.imageInfo)
			}
		}

	}
</script>

<style>
	.square-root {
		height: 100%;
	

	}

	.line {
		height: 2rpx;
		background-color: #f3f3f3;
	}

	/* tab导航 */
	.tab-nav-main-wrapper {
		display: flex;
		flex-direction: row;
		justify-content: center;
		border-bottom: 2rpx solid #f3f3f3;
	}

	.tab-nav-main {
		display: inline-flex;
		flex-direction: row;
		justify-content: center;
		position: relative;
	}

	.tab-nav-list {
		display: flex;
		flex-direction: row;
		justify-content: center;
		font-size: 0;
	}

	.tab-nav-item {
		font-size: 30rpx;
		/* line-height: 76rpx; */
		padding: 0 20rpx;
		padding-bottom: 20rpx;
		margin: 0 8rpx;
	}

	.tab-nav-item-active {
		font-weight: bold;
	}

	.tab-nav-underline {
		position: absolute;
		bottom: -2rpx;
		left: 4rpx;
		height: 4rpx;
		border-radius: 2rpx;
		background-color: #f6423d;
	}

	.tab-nav-underline-animation {
		transition-duration: 200ms;
		transition-property: left;
	}

	/* tab内容 */
	.tab-content {
		height: 100%;
	}

/* 	.tab-content-item {
		overflow-y: auto;
	} */

	/* 发布动态按钮 */
	.open-popup {
  position: fixed;
bottom: 30rpx;
  left: 30rpx;
		
		width: 88rpx;
		height: 88rpx;
		border-radius: 50%;
		background-color: #fd4445;

		/* position: absolute;
		bottom: 30upx;
		left: 30rpx;
		width: 88rpx;
		height: 88rpx;
		border-radius: 50%;
		background-color: #fd4445; */
	}

	.open-popup-horizontal {
		width: 46rpx;
		height: 4rpx;
		background-color: #ffffff;
		position: absolute;
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
		margin: auto;
	}

	.open-popup-vertical {
		width: 4rpx;
		height: 46rpx;
		background-color: #ffffff;
		position: absolute;
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
		margin: auto;
	}

	/* 发动态弹层 */
	.popup {
		/* #ifdef H5 */
		height: 320rpx;
		/* #endif */
		/* #ifndef H5 */
		height: 220rpx;
		/* #endif */

		border-top-left-radius: 50rpx;
		border-top-right-radius: 50rpx;
		background-color: #ffffff;
		display: flex;
		flex-direction: row;
		justify-content: space-around;
		align-items: flex-start;
		padding-top: 40rpx;
		font-size: 30rpx;
		line-height: 2;
		color: #7F7F7F;
		text-align: center;
	}

	.popup .popup-publish-image-wrapper {
		background-color: #fd4445;
		width: 132rpx;
		height: 132rpx;
		border-radius: 50%;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.popup-publish-image-moment {
		width: 60rpx;
		height: 60rpx;
	}

	.popup-publish-image-video {
		width: 64rpx;
		height: 64rpx;
	}
</style>
