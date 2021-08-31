<template>
	<view class="activity-list-root common-padding">
		<view>
		<!-- <view class="activity-item" v-for="(activity,index) in activityInfo.activityList" @click="goToActivityMomentList(activity.id)"> -->
		<view class="activity-item" v-for="(activity,index) in activityInfo.activityList" >
		
			<image class="activity-item-image" :src="activity.posterImage" mode="aspectFill" :data-image-index="index" @click.stop="previewImage(index)"></image>
			<view class="activity-item-info" @click="toWeb(activity.sourceUrl)">
				<view class="activity-item-name">
					{{activity.name}}
				</view>
				<view class="activity-item-time-address">
					地址：{{activity.venueCity}} 
				</view>
				<view class="activity-item-time-address" >
					{{activity.venueName}}
	
				</view>
				
				<view class="activity-item-time-addresss" >
					参与:{{activity.venueAddress}}
					
				</view>
				<view class="activity-item-time-addresss2" >
					价格：{{activity.sourceChannel}}
					
				</view>
				<view class="activity-item-time-address" href="http://www.dclound.io/">
					时间：{{activity.showStartTime}}
				</view>
				<view :class="activity.hasJoined ? 'activity-item-has-joined-true' : 'activity-item-has-joined-false' " @click.stop="joinedActivity(activity)">
					{{activity.hasJoined ? '取消报名' : '报名'}}
				</view>
				
			</view>
			
			
		</view>
		
		<!-- 弹窗 -->
		<uni-popup ref="popup" type="center">
			<view class="popup-content" v-model="activityInfo">
				<view class="popup-title">
					{{currentFieldDesc}}
				</view>
				<input ref="inputValue" :focus="inputFocus" placeholder="请输入联系方式" class="popup-desc" type="text" v-model="currentFieldValue"  />
				
				<view class="popup-button">
					<view class="popup-cancel" @click="cancelPopup">
						取消
					</view>
					<view class="popup-confirm" @click="confirmPopup">
						确认
					</view>
				</view>
			</view>
		</uni-popup>
</view>
	</view>
</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';

	export default {
		data() {
			return {
				currentFieldName: "报名联系电话",
				currentFieldDesc: "留下手机号，主办方才能联系到你",
				currentFieldValue: "",
				// ALL，JOINED
				pageType: "",
				tabel1:"",
				tabel2:"",
				tabel3:"",
				// 动态数据
				activityInfo: {
					pageNo: 1,
					pageSize: 20,
					activityList: []
				},
				imageList:  [
									],

			}
		},
		async onLoad(options) {
			this.pageType = options.pageType;
			// 加载演出数据
			await this.loadActivityInfo();
		},

		async onPullDownRefresh() {
			this.activityInfo = {
				pageNo: 1,
				pageSize: 20,
				activityList: []
			};
			await this.loadActivityInfo();
			uni.stopPullDownRefresh();
		},

		async onReachBottom() {
			console.log("触底");
			await this.loadActivityInfo();
		},

		methods: {
			toWeb(url){
				
				uni.navigateTo({
					url:`/pages/webView/webView?url=${url}`
					
				})
			},
			// 事件相关函数
			// goToActivityMomentList(activityId) {
			// 	uni.navigateTo({
			// 		url: `/pages/momentList/momentList?pageType=ACTIVITY_MOMENT_LIST&activityId=${activityId}`
			// 	});
			// },

			// 数据相关函数
			// 加载演出数据
			async loadActivityInfo() {
				uni.showLoading({
					title: '数据加载中...'
				});

				let api = "";
				if (this.pageType === "ALL") {
					api = "/activity/queryActivity";
				} else {
					api = "/activity/queryJoinedActivity";
				}

				let [data] = await httpUtils.postJson(api, {
					pageNo: this.activityInfo.pageNo,
					pageSize: this.activityInfo.pageSize
				});
				this.activityInfo.activityList = this.activityInfo.activityList.concat(data.body);
				this.activityInfo.pageNo++;

				uni.hideLoading();
			},
			cancelPopup() {
				
				this.$refs.popup.close();
			},
			async confirmPopup() {
				if(this.currentFieldValue.length==0|| !(/(^[1-9]\d*$)/.test(this.currentFieldValue))){
					uni.showToast({
						duration:1000,
						title:"请留正确手机号"
					})
				}else{
					//this.tabel2=!this.tabel2;
					let [data] = await httpUtils.postJson("/activity/joinedActivity", {
						activityId: this.tabel1,
						hasJoined: this.tabel2,
						phoneId:this.currentFieldValue
					});
					uni.redirectTo({
						url:"/pages/activityList/activityList?pageType=ALL"
					})
					this.$refs.popup.close();
				}
				
			},
				
			async joinedActivity(activity) {
				if(!activity.hasJoined){
					this.tabel2=!activity.hasJoined;
					this.tabel1=activity.id;
					
					this.$refs.popup.open();
				}else{
					let [data] = await httpUtils.postJson("/activity/joinedActivity", {
						activityId: activity.id,
						hasJoined: !activity.hasJoined,
						
					});
					activity.hasJoined=!activity.hasJoined;
				}
				
				
				// if(this.tabel2!=true&&this.tabel2!=false){
				// 	this.tabel2=activity.hasJoined;
				// }
				
				// 	if(!this.tabel2){
				// 		this.tabel1=activity.id;
				// 		//this.tabel2=!this.tabel2;
						
				// 		this.$refs.popup.open(activity);
				// 	}else{
				// 		let [data] = await httpUtils.postJson("/activity/joinedActivity", {
				// 			activityId: activity.id,
				// 			hasJoined: !activity.hasJoined,
							
				// 		});
				// 		this.tabel2=!this.tabel2;
				// 	}
					
				
				
			},
			// 图片预览
			previewImage(index) {
				
				let imgs = this.activityInfo.activityList.map(activity =>{
                //只返回图片路径
					return activity.posterImage})
				
				uni.previewImage({
					urls: imgs,
					current: index
					//current:0
				});
				console.log(imgs)
			}

		}
	}
</script>

<style>
	.activity-list-root {
		padding-top: 30rpx;
	}

	.activity-item {
		display: flex;
		flex-direction: row;
		margin-bottom: 76rpx;
	}

	.activity-item-image {
		width: 260rpx;
		height: 360rpx;
		flex-shrink: 0;
		margin-right: 30rpx;
		border-radius: 6rpx;
	}

	.activity-item-info {
		width: 370rpx;
		position: relative;
	}

	.activity-item-name {
		font-size: 34rpx;
		color: #090A0C;
		margin-bottom: 20rpx;
		max-height: 146rpx;
		overflow: hidden;
	}

	.activity-item-time-address {
		height: 42rpx;
		font-size: 28rpx;
		color: #ADAEAF;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}
.activity-item-time-addresss {
		height: 42rpx;
		font-size: 29rpx;
		color: #ba6400;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}
	.activity-item-time-addresss2 {
			height: 37rpx;
			font-size: 28rpx;
			text-decoration:underline;
			color: #c1000c;
			white-space: nowrap;
			overflow: hidden;
			text-overflow: ellipsis;
		}
	.activity-item-has-joined-false {
		position: absolute;
		bottom: 0;
		left: 0;

		background-color: white;
		color: #DA261B;
		border: 2rpx solid #DA261B;

		width: 118rpx;
		height: 50rpx;
		font-size: 28rpx;
		letter-spacing: 2rpx;
		line-height: 50rpx;
		text-align: center;
		border-radius: 26rpx;
		padding: 0 6rpx;
	}

	.activity-item-has-joined-true {
		position: absolute;
		bottom: 0;
		left: 0;

		background-color: white;
		color: #999999;
		border: 2rpx solid #d8d8d8;

		width: 118rpx;
		height: 50rpx;
		font-size: 28rpx;
		letter-spacing: 2rpx;
		line-height: 50rpx;
		text-align: center;
		border-radius: 26rpx;
		padding: 0 6rpx;
	}
	/* 弹窗 */
	.popup-content {
		position: relative;
		background-color: #ffffff;
		width: 602rpx;
		border-radius: 12rpx;
	}
	
	.popup-title {
		font-size: 30rpx;
		font-weight: bold;
		height: 130rpx;
		box-sizing: border-box;
		text-align: center;
		padding-top: 60rpx;
		color: #121212;
	}
	
	.popup-desc {
		text-align: center;
		font-size: 32rpx;
		padding-bottom: 188rpx;
		padding-left: 50rpx;
		padding-right: 50rpx;
		display: flex;
		flex-direction: row;
		justify-content: center;
		color: #1C1C1C;
		border-bottom: 2rpx solid #F1F1F1;
	}
	
	.popup-button {
		position: absolute;
		bottom: 0;
		left: 0;
		display: flex;
		flex-direction: row;
		border-top: 2rpx solid #F1F1F1;
	}
	
	.popup-cancel {
		font-size: 32rpx;
		width: 300rpx;
		height: 110rpx;
		line-height: 110rpx;
		text-align: center;
		border-right: 2rpx solid #F1F1F1;
		color: #0B0B0B;
		font-weight: bold;
	}
	
	.popup-confirm {
		font-size: 32rpx;
		width: 300rpx;
		height: 110rpx;
		line-height: 110rpx;
		text-align: center;
		color: #f6423d;
		font-weight: bold;
	}
	
	.popup-cancel:active,
	.popup-confirm:active {
		background-color: #f0f0f0;
	}
</style>
