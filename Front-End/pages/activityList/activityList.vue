<template>
	<view class="activity-list-root common-padding">
		<view>
		<!-- <view class="activity-item" v-for="(activity,index) in activityInfo.activityList" @click="goToActivityMomentList(activity.id)"> -->
		<view class="activity-item" v-for="(activity,index) in activityInfo.activityList" >
		
			<image class="activity-item-image" :src="activity.posterImage" mode="aspectFill" :data-image-index="index" @click.stop="previewImage(index)"></image>
			
			<view class="seperateLine">	</view>	
			
			<view class="activity-item-info" @click="toWeb(activity.sourceUrl)">
				<!-- 活动名 -->
				<view class="activity-item-name">
					{{activity.name}}
				</view>
				<view class="activity-item-time-address" href="http://www.dclound.io/">
					时间：
				</view>
				<view class="activity-item-time-address" href="http://www.dclound.io/">
					{{activity.showStartTime}}
				</view>
				<view class="activity-item-time-address">
					地点：{{activity.venueCity}} <!-- {{activity.venueName}} -->
				</view>
				<view class="activity-item-time-address" >
					{{activity.venueName}}
					
				</view>
				
				<view class="activity-item-time-addresss" >
					点击了解详情{{activity.venueAddress}}
					
				</view>
				<!-- <view class="activity-item-time-addresss2" >
					价格：{{activity.sourceChannel}}
					
				</view> --> 
<!-- 				<image :src="collectIcon" mode="widthFix" style="width: 40rpx;margin: 0;position: relative;left: 280rpx;" @click="collect"></image>
				 -->
				 <!-- 报名按钮 -->
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
			collect(){
				if(this.isCollected == false)
					{
						this.isCollected = true
						this.collectIcon = "../../static/icon/collect.png"
					}
				else
					{
						this.isCollected = false
						this.collectIcon = "../../static/icon/collected.png"
					}
			},
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

		},
		computed:{
			collectIcon(){
				return (this.isCollected == false ? "../../static/icon/collect.png" : "../../static/icon/collected.png")
			}
		},
	}
</script>

<style>
	.activity-list-root {
		padding-top: 30rpx;
	}

	.activity-item {
		/* display: flex;
		flex-direction: row;
		margin-bottom: 76rpx; */
		display: flex;
		margin-top: 10rpx;
		width: 96%;
		margin-left: 2%;
		height: 400rpx;
		background-color: #FFFFFF;
		border-radius: 15rpx;
		box-shadow: 0px 2px 2px #cdcfcf;
		}
	

	.activity-item-image {
		/* width: 260rpx;
		height: 360rpx;
		flex-shrink: 0;
		margin-right: 30rpx;
		border-radius: 6rpx; */
		margin: 10rpx;
		margin-bottom: 0;
		margin-top: 15rpx;
		margin-left: 20rpx;
		width: 260rpx;
		height: 360rpx;
		border-radius: 10rpx;
	}

	.activity-item-info {
	width: 370rpx;
	position: relative;
		}
	

	.activity-item-name {
		/* font-size: 40rpx;
		color: #090A0C;
		margin-bottom: 20rpx;
		max-height: 146rpx;
		overflow: hidden; */
		font-size: large;
		font-weight: 700;
		display: block;
		margin-bottom: 10rpx;
		margin-top: 15rpx;
	}

	.activity-item-time-address {
		/* height: 42rpx;
		font-size: 28rpx;
		color: #ADAEAF;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis; */
		display: block;
		font-size: small;
		margin-bottom: 10rpx;
		color: #828282;
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
		bottom: 21rpx;
		left: 0rpx;

		background-color: white;
		color: #DA261B;
		/* color: #000000; */
		border: 1rpx solid #DA261B;

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
	.seperateLine{
		padding:62px 6px 0px 6px;
		margin-left: 6px;
		margin-top: 5rpx;
		border-left: 2px solid #d5d0ce;
		font-size: 0;
		height: 260rpx;
		flex: 0.05;
	}
</style>
