<template>
	<view class="moment-list-root">


		<view class="moment-list-title" >
			只显示未审核与被拒绝的投递记录
		</view>



		<!-- 动态列表 -->
		<myymoment v-for="(moment) in momentInfo.momentList" :moment="moment" momentPageType="LIST" v-on:updateMomentLikeCount="updateMomentLikeCount($event,moment)"
		 v-on:updateMomentHasLiked="updateMomentHasLiked($event,moment)">
		</myymoment>
	</view>
	

</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';
	import myymoment from '../../components/moment/myymoment.vue';

	export default {
		components: {
			myymoment
		},

		data() {
			return {
				momentInfo: {
					pageNo: 1,
					pageSize: 20,
					momentList: []
				},
				zdmomentInfo: {
					pageNo: 1,
					pageSize: 20,
					zdmomentList: []
				},
				swiperList: [{
					id: 0,
					type: 'image',
					url: 'https://ossweb-img.qq.com/images/lol/web201310/skin/big84000.jpg'
				}, {
					id: 1,
					type: 'image',
					url: 'https://ossweb-img.qq.com/images/lol/web201310/skin/big37006.jpg',
				}, {
					id: 2,
					type: 'image',
					url: 'https://ossweb-img.qq.com/images/lol/web201310/skin/big39000.jpg'
				}],
				
				// 页面类型：FRIEND_MOMENT_LIST，ACTIVITY_MOMENT_LIST，TOPIC_MOMENT_LIST
				pageType: "",
				
				// 演出id
				activityId: null,

				// 话题
				topicName: null,
				topic: {},
				
				
			}
		},

		async onLoad(options) {
			this.pageType = options.pageType;
			this.activityId = options.activityId;
			this.topicName = options.topicName;
			this.userId=options.userId;
			//console.log(options.userId);
			// 加载置顶moment数据
			// await this.loadZDMomentInfo();
			// 加载moment数据
			await this.loadMomentInfo();
			
			
			uni.$on('updateMomentHasFollowed', event => {
				for (let i in this.momentInfo.momentList) {
					let moment = this.momentInfo.momentList[i];
					if (moment.id === event.id) {
						moment.hasFollowed = event.hasFollowed;
						break;
					}
				}
			});
		},

		async onPullDownRefresh() {
			this.momentInfo = {
				pageNo: 1,
				pageSize: 7,
				momentList: []
			};
			await this.loadMomentInfo();
			uni.stopPullDownRefresh();
		},

		async onReachBottom() {
			console.log("触底");
			await this.loadMomentInfo();
		},

		methods: {
			getDetailzd(momentId){
				console.log("111");
				uni.navigateTo({
					url: `../../pages/zdmomentDetail/zdmomentDetail?momentId=${momentId}`
				});
			},
			// 事件相关函数
			updateMomentLikeCount(event, moment) {
				moment.likeCount = event;
			},

			updateMomentHasLiked(event, moment) {
				moment.hasLiked = event;
			},

			updateTopicCoverUrl() {
				let loginUser = uni.getStorageSync("loginUser");
				if (this.topic.createUserId !== loginUser.userId) {
					return;
				}

				uni.chooseImage({
					count: 1,
					sizeType: ['original'],
					sourceType: ['album'],
					success: async (res) => {
						let tempFilePath = res.tempFilePaths[0];

						uni.showLoading({
							title: '上传中...'
						});
						let [url] = await httpUtils.uploadFile(tempFilePath);
						uni.hideLoading();

						await httpUtils.postJson("/topic/updateTopicCoverUrl", {
							topicId: this.topic.id,
							coverUrl: url
						});
						this.topic.coverUrl = url;
					}
				});
			},

			// 加载置顶moment数据
			async loadZDMomentInfo() {
				let [data] = await httpUtils.postJson("/square/querySomeZDMoment", {
					pageNo: this.zdmomentInfo.pageNo,
					pageSize: this.zdmomentInfo.pageSize,
					userId: this.userId
				});
				this.zdmomentInfo.zdmomentList = this.zdmomentInfo.zdmomentList.concat(data.body);
				//console.log(this.zdmomentInfo.zdmomentList[0].url);
				this.zdmomentInfo.pageNo++;
			},
			// 数据相关函数
			// 加载moment数据
			async loadMomentInfo() {
				//console.log(this.userId);
				//if (this.pageType === 'FRIEND_MOMENT_LIST2') {//查关注的某个人的动态
						let [data] = await httpUtils.postJson("/square/queryMyMoment", {
							pageNo: this.momentInfo.pageNo,
							pageSize: this.momentInfo.pageSize,
							userId: this.userId
						});
						this.momentInfo.momentList = this.momentInfo.momentList.concat(data.body);
						this.momentInfo.pageNo++;
					//}
				// let [data] = await httpUtils.postJson("/square/querySquareMoment", {
				// 	pageNo: this.momentInfo.pageNo,
				// 	pageSize: this.momentInfo.pageSize
				// });
				// this.momentInfo.momentList = this.momentInfo.momentList.concat(data.body);
				// this.momentInfo.pageNo++;
				
			},
			// async loadMomentInfo() {
			// 	if (this.pageType === 'FRIEND_MOMENT_LIST') {
			// 		let [data] = await httpUtils.postJson("/discover/queryFriendMoment", {
			// 			pageNo: this.momentInfo.pageNo,
			// 			pageSize: this.momentInfo.pageSize
			// 		});
			// 		this.momentInfo.momentList = this.momentInfo.momentList.concat(data.body);
			// 		this.momentInfo.pageNo++;
			// 	}
			// 	if (this.pageType === 'FRIEND_MOMENT_LIST2') {//查关注的某个人的动态
			// 		let [data] = await httpUtils.postJson("/square/querySomeMoment", {
			// 			pageNo: this.momentInfo.pageNo,
			// 			pageSize: this.momentInfo.pageSize,
			// 			userId: this.userId
			// 		});
			// 		this.momentInfo.momentList = this.momentInfo.momentList.concat(data.body);
			// 		this.momentInfo.pageNo++;
			// 	}

			// 	if (this.pageType === 'ACTIVITY_MOMENT_LIST') {
			// 		let [data] = await httpUtils.postJson("/square/querySquareMoment", {
			// 			pageNo: this.momentInfo.pageNo,
			// 			pageSize: this.momentInfo.pageSize,
			// 			activityId: this.activityId
			// 		});
			// 		this.momentInfo.momentList = this.momentInfo.momentList.concat(data.body);
			// 		this.momentInfo.pageNo++;
			// 	}

			// 	if (this.pageType === 'TOPIC_MOMENT_LIST') {
			// 		if (!this.topic.id) {
			// 			let [topicDetailData] = await httpUtils.postJson("/topic/getTopicDetail", {
			// 				name: this.topicName
			// 			});
			// 			this.topic = topicDetailData.body;
			// 		}

			// 		let [data] = await httpUtils.postJson("/topic/queryTopicMoment", {
			// 			pageNo: this.momentInfo.pageNo,
			// 			pageSize: this.momentInfo.pageSize,
			// 			topicId: this.topic.id
			// 		});
			// 		this.momentInfo.momentList = this.momentInfo.momentList.concat(data.body);
			// 		this.momentInfo.pageNo++;
			// 	}
			// }
		}
	}
</script>

<style>
	.moment-list-root {
		padding-top: 10rpx;
	}

	.moment-list-topic {
		height: 470rpx;
		background-position: center;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		border-bottom: 12rpx solid #F5F5F5;
	}

	.moment-list-topic-name {
		text-align: center;
		position: relative;
		font-size: 36rpx;
		color: white;
		padding: 20rpx 30rpx;
		border-top: 4rpx solid hsla(0, 0%, 100%, .39);
		border-bottom: 4rpx solid hsla(0, 0%, 100%, .39);
	}

	.moment-list-topic-user-count {
		font-size: 28rpx;
		color: white;
		margin-top: -20rpx;
		z-index: 1;
	}

	.moment-list-title {
		padding: 0 30rpx;
		font-size: 30rpx;
		font-weight: bold;
		margin-top: 30rpx;
		margin-bottom: 10rpx;
		color: #444;
	}
	
	/* 新增 */
	.moment-root {
		display: flex;
		flex-direction: row;
		margin-top: 40rpx;
		padding: 0 30rpx 30rpx 30rpx;
		font-size: 30rpx;
	}
	
	.moment-root-border {
		border-bottom: 1rpx solid #f3f3f3;
	}
	
	/* 头像 */
	.moment-avatar {
		width: 80rpx;
		height: 80rpx;
		flex-shrink: 0;
		border-radius: 50%;
		transition: transform 1s;
	}
	
	.moment-avatar:active {
		transform: rotateZ(1800deg)
	}
	
	.moment-avatar-list-margin {
		margin-right: 24rpx;
	}
	
	.moment-avatar-detail-margin {
		margin-right: 20rpx;
	}
	
	/* 用户信息 */
	.moment-user-info {
		display: flex;
		flex-direction: row;
	}
	
	/* 内容 */
	.moment-list-content {
		width: 590rpx;
	}
	
	.moment-detail-content {
		width: 690rpx;
	}
	
	.moment-user-time-wrapper {
		height: 80rpx;
		width: 100%;
		position: relative;
	}
	
	.moment-user-name-wrapper {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
	}
	
	.moment-user-name {
		color: #577C9F;
		height: 44rpx;
		line-height: 44rpx;
	}
	
	.moment-notification-type {
		height: 44rpx;
		line-height: 42rpx;
		font-size: 26rpx;
		color: #333;
		margin-left: 10rpx;
		font-weight: 400;
	}
	
	.moment-create-time {
		color: #A1A1A1;
		font-size: 26rpx;
		height: 40rpx;
		line-height: 40rpx;
	}
	
	.moment-create-time-right {
		position: absolute;
		right: 0;
		top: 2rpx;
		font-size: 22rpx;
	}
	
	.moment-text-content {
		margin-top: 10rpx;
		margin-bottom: 12rpx;
		color: #333333;
		line-height: 46rpx;
	}
	
	.moment-text-content-no-margin-top {
		margin-top: 0;
	}
	
	/* 转发 */
	.moment-repost-area {
		background-color: #F4F4F4;
		border-radius: 8rpx;
		padding: 14rpx 14rpx 18rpx 14rpx;
		font-size: 28rpx;
	}
	
	.moment-repost-user-text-content {
		margin-bottom: 10rpx;
	}
	
	.moment-repost-user-name {
		color: #577C9F;
		line-height: 46rpx;
	}
	
	.moment-repost-text-colon {
		margin: 0 -8rpx 0 4rpx;
		line-height: 46rpx;
	}
	
	.moment-repost-text-content {
		color: #666;
		line-height: 46rpx;
		width: 10rpx;
		
	}
	
	/* 操作 */
	.moment-operation-area {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		margin: 30rpx 0 0 0;
		color: #A1A1A1;
		font-size: 25rpx;
	}
	
	.moment-operation-left {
		display: flex;
		flex-direction: row;
	}
	
	.moment-operation-item {
		display: flex;
		flex-direction: row;
		align-items: center;
		margin-right: 62rpx;
	}
	
	.moment-operation-like-text-active {
		color: #db301f;
	}
	
	.moment-operation-share {
		margin-right: 0;
		background-color: #ffffff;
		padding: 0;
	}
	
	.moment-operation-share image {
		margin-right: 0;
	}
	
	.moment-operation-share:after {
		width: 38rpx;
		height: 38rpx;
	
		border: none;
	}
	
	.moment-operation-icon {
		width: 38rpx;
		height: 38rpx;
		margin-right: 8rpx;
	}
	
</style>
