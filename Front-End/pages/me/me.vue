<template>
	<view class="me-root">

		<view class="me-user-wrapper" @click="pguserInfo" >
			<!-- <navigator class="me-item me-item-margin" url="/pages/userInfo/userInfo"> -->
			<!-- 头像 -->
			<image class="me-user-avatar" mode="aspectFill" :src="userInfo.avatarUrl"></image>

			<view class="me-user-info">
				<!-- 用户名 -->
				<view class="me-user-user-name">
					<text>{{userInfo.name}}</text>
				
				</view>
				<!-- 个人简介 -->
				<view class="me-user-description">
					{{userInfo.university}}
				</view>
			</view>
			<view class="me-go-to">
			</view>
			<!-- </navigator> -->
		</view>

		<view class="me-list">
			<navigator class="me-item me-item-margin" url="/pages/activityList/activityList?pageType=ALL">
				<image class="me-item-icon" src="../../static/icon/activity-me.png" mode="aspectFill"></image>
				<view class="me-item-title">
					
					有趣的活动o(*≧▽≦)ツ
				</view>
				<view class="me-item-go-to">
				</view>
			</navigator>
			<navigator class="me-item" url="/pages/friendList/friendList" >
				<image class="me-item-icon" src="../../static/icon/me-friend.png" mode="aspectFill"></image>
				<view class="me-item-title me-item-title-border">
					好友
				</view>
				<view class="me-item-go-to">
				</view>
			</navigator>

			<navigator class="me-item" @click="loadmymoment(userInfo.id)" >
				<image class="me-item-icon" src="../../static/icon/me-collection.png" mode="aspectFill"></image>
				<view class="me-item-title me-item-title-border">
					我的投递
				</view>
				<view class="me-item-go-to">
				</view>
			</navigator>

			<navigator  v-show="!flag" class="me-item" url=" " @click="loaddifference">
				<image class="me-item-icon" src="../../static/icon/discover-activity.png" mode="aspectFill"></image>
				<view class="me-item-title me-item-title-border">
					审核
				</view>
				<view class="me-item-go-to">
				</view>
			</navigator>

<navigator v-show="!flag" class="me-item" url="/pages/publishzdMoment/publishzdMoment">
				<image class="me-item-icon" src="../../static/icon/discover-activity.png" mode="aspectFill"></image>
				<view class="me-item-title me-item-title-border">
					投放
				</view>
				<view class="me-item-go-to">
				</view>
			</navigator>
			<navigator class="me-item me-item-margin" url=""@click="loadNotification">
				<image class="me-item-icon" src="../../static/icon/me-notification.png" mode="aspectFill"></image>
				<view class="me-item-title">
					
					<view class="tab-nav-main-wrapper">
						<view class="tab-nav-main">
							<view class="tab-nav-list" ref="tabNavList">
								
								<view class="tab-nav-item" :class="curTabIndex==0 ? 'tab-nav-item-active' : ''" :data-index="0" @click="clikeTabNav">
									通知
									<text class="tab-nav-item-num" v-if="countInfo.commentUnread+countInfo.atUnread > 0">{{countInfo.commentUnread+countInfo.atUnread > 99 ? 99 : countInfo.commentUnread+countInfo.atUnread}}</text>
								</view>
								
							</view>
						</view>
					</view>
				
				</view>
				<view class="me-item-go-to">
				</view>
			</navigator>



		<!-- 	<navigator class="me-item me-item-margin" url="/pages/notification/notification">
				<image class="me-item-icon" src="../../static/icon/me-notification.png" mode="aspectFill"></image>
				<view class="me-item-title">
					消息
				</view>
				<view class="me-item-go-to">
				</view>
			</navigator> -->

			<navigator class="me-item" url="/pages/settingList/settingList">
				<image class="me-item-icon" src="../../static/icon/me-setting.png" mode="aspectFill"></image>
				<view class="me-item-title">
					认证与反馈
				</view>
				<view class="me-item-go-to">
				</view>
			</navigator>
			
		</view>
		
	</view>
</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';


	export default {
		 data() {
			return {
				flag:true,
				userInfo: {},
				// 数量
				countInfo: {
					commentUnread: 0,
					atUnread: 0,
					notificationUnread: 0
				},
			}
		},
		async onLoad(){
			
			//await this.loadCountInfo();
			
			await this.loaduser();
			
			
			
			if(this.userInfo.gender==99){
				this.flag=false;
			}
			
			
		},
		async onShow() {
			// 加载数据
			//await this.loadUserInfo();
			await this.loaduser();
			
			// await this.loadCountInfo();
			
			
		},
		

		methods: {
			
			async loaduser(){
				let [userInfoData] = await httpUtils.postJson("/user/getUserInfo", {});
				this.userInfo = userInfoData.body;
				this.countInfo = userInfoData.body;
			},
			// 数据相关函数
			// 加载个数数据
			// async loadCountInfo() {

			// 	let [countData] = await httpUtils.postJson("/user/getUserInfo", {});
			// 	this.countInfo = countData.body;
				
			// },
			
			//  loadUserInfo() {
			// 	let [userInfoData] = await httpUtils.postJson("/user/getUserInfo", {});
			// 	this.userInfo = userInfoData.body;
			// 	console.log(this.userInfo);
					
				
			// },
			pguserInfo(){
				uni.navigateTo({
					url: `/pages/userInfo/userInfo`
				});
			},
			loadNotification(){
				this.countInfo.commentUnread = 0;
				this.countInfo.atUnread = 0;
				uni.removeTabBarBadge({
					index: 2,
				});
				uni.navigateTo({
					url: `/pages/notification/notification`
				});
			},
			loadmymoment(e){
				//let myurl=`/pages/myMoment/myMoment?userId=${e}`;
				//console.log(e);

				uni.navigateTo({
					//url: `/pages/myMoment/myMoment?userId=${e}`
					url: `/pages/myMoment/myMoment?pageType=FRIEND_MOMENT_LIST2&userId=${e}`
					//url: `/pages/test/test`			
				});
			},
			loaddifference() {
				
				
				
				 
				if(this.userInfo.gender==99){
					uni.navigateTo({
						url: `/pages/verify/verify`
					});
				}else{
					
					uni.navigateTo({
						url: `/pages/publishMoment/publishMoment`
					});
				}
				
			},
		}
	}
</script>

<style>
	.me-root {
		background-color: #EDEDED;
		height: 100%;
	}

	/* 用户信息 */
	.me-user-wrapper {
		background-color: white;
		padding-left: 46rpx;
		padding-right: 30rpx;
		padding-bottom: 70rpx;
		display: flex;
		flex-direction: row;
		padding-top: 20rpx;
		position: relative;
		margin-bottom: 16rpx;
	}

	.me-user-avatar {
		width: 116rpx;
		height: 116rpx;
		flex-shrink: 0;
		border-radius: 10rpx;
		margin-right: 30rpx;
	}

	.me-user-info {
		width: 580rpx;
	}

	.me-user-user-name {
		margin-top: 10rpx;
		font-size: 40rpx;
		color: #1C1C1C;
		position: relative;
	}

	.me-user-gender {
		position: absolute;
		width: 28rpx;
		height: 28rpx;
		top: 12rpx;
		left: 90rpx;
	}

	.me-user-description {
		margin-top: 6rpx;
		font-size: 28rpx;
		color: #7B7B7B;
	}

	.me-go-to {
		position: absolute;
		top: 107rpx;
		right: 30rpx;
		width: 14rpx;
		height: 14rpx;
		border-top: 4rpx solid #ACACAC;
		border-right: 4rpx solid #ACACAC;
		transform: rotate(45deg);
	}

	/* 功能列表 */
	.me-item-margin {
		margin-bottom: 16rpx;
	}

	.me-item {
		position: relative;
		padding-left: 30rpx;
		display: flex;
		flex-direction: row;
		align-items: center;
		background-color: white;
		color: #111111;
	}

	.me-item:active {
		background-color: #D5D5D5;
	}

	.me-item-icon {
		width: 50rpx;
		height: 50rpx;
		margin-right: 30rpx;
	}

	.me-item-title {
		font-size: 32rpx;
		width: 640rpx;
		height: 108rpx;
		line-height: 108rpx;
	}

	.me-item-title-border {
		border-bottom: 1rpx solid #f3f3f3;
	}

	.me-item-go-to {
		position: absolute;
		top: 46rpx;
		right: 30rpx;
		width: 14rpx;
		height: 14rpx;
		border-top: 4rpx solid #ACACAC;
		border-right: 4rpx solid #ACACAC;
		transform: rotate(45deg);
	}
	
	
	/* tab导航 */
	.tab-nav-main-wrapper {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		border-bottom: 2rpx solid #f3f3f3;
		
	}
	
	.tab-nav-main {
		display: inline-flex;
		flex-direction: row;
		justify-content: flex-start;
		position: relative;
	}
	
	.tab-nav-list {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		font-size: 0;
	}
	
	.tab-nav-item {
		box-sizing: border-box;
		font-size: 30rpx;
		
		
		position: relative;
	}
	
	.tab-nav-item-num {
		font-size: 24rpx;
		color: white;
		font-weight: 300;
		position: absolute;
		right: -32rpx;
		top: 3rpx;
		width: 40rpx;
		height: 40rpx;
		line-height: 40rpx;
		text-align: center;
		border-radius: 50%;
		background-color: #f6423d;
		transform: scale(0.7);
	}
	
	.tab-nav-item-active {
		font-weight: bold;
		color: #f6423d;
		border-bottom: 4rpx solid #f6423d;
	
	}
	
	.tab-nav-underline {
		position: absolute;
		bottom: -2rpx;
		left: 8rpx;
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
	
	.tab-content-item {
		overflow-y: auto;
	}
</style>
