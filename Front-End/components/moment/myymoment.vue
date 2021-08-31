<template>
	<view class="moment-root" :class="momentPageType === 'LIST' ? 'moment-root-border' : '' " >
		<!-- 头像 -->
		<image class="moment-avatar" :class="momentPageType === 'LIST' ? 'moment-avatar-list-margin' : '' " :src="moment.userAvatarUrl"
		 mode="aspectFill" v-if="momentPageType === 'LIST'" ></image>

		<view :class="momentPageType === 'LIST' ? 'moment-list-content' : 'moment-detail-content' ">

			<view class="moment-user-info">
				<!-- 头像 -->
				<image class="moment-avatar" :class="momentPageType === 'DETAIL' ? 'moment-avatar-detail-margin' : '' " :src="moment.userAvatarUrl"
				 mode="aspectFill" v-if="momentPageType === 'DETAIL' " ></image>

				<!-- 用户名，发布时间 -->
				<view class="moment-user-time-wrapper">
					<view class="moment-user-name-wrapper">
						<view class="moment-user-name">
							{{moment.userName}}
						</view>
						<view class="moment-notification-type">
							{{notificationTypeDesc}}
						</view>
					</view>
					<view class="moment-create-time" :class="fromNotification ? 'moment-create-time-right' : '' ">
						{{moment.createTime | chineseTimeFormat}}
						
					</view>
				</view>
			</view>

			<!-- 	<view class="uni-title uni-common-mt">
				                
				                <text>字符串类型\nnodes属性为String</text>
				            </view> -->
			<!-- 动态内容 -->
			<view class="moment-text-content" :class="fromNotification ? 'moment-text-content-no-margin-top' : '' ">
				<!-- <u-parse v-if="moment.textContent" :content="moment.textContent"></u-parse> -->
				<text>{{moment.textContent}}</text>
			</view>

			

			<!-- 视频区域 -->
			<view class="moment-video-area" v-if="moment.repostMomentId === 0 && moment.type === 'VIDEO'" @click.stop="momentPageType === 'LIST' && goToMomentDetail(moment.id) || momentPageType === 'DETAIL' && goToVideoDetail(moment.id)">
				<momentVideo :moment="moment"></momentVideo>
			</view>
			<!-- 图片区域 -->
			<view class="moment-image-area" v-if="moment.repostMomentId === 0 && moment.type === 'IMAGE' ">
				<momentImage :moment="moment"></momentImage>
			</view>
<view class="moment-operation-area" >
<view v-if="moment.activityId===3" class="moment-operation-share moment-operation-item"  @click.stop="toPublishMoment(moment.id)">
					<image src="../../static/icon/moment-operation-edit.png" class="moment-operation-icon"></image>
				</view>
				<view class="moment-operation-share moment-operation-item"  @click.stop="deleteMoment(moment.id)">
									<image src="../../static/icon/delete.png" class="moment-operation-icon"></image>
								</view>
			</view>
			</view>

		</view>
	</view>
</template>

<script>
	import uParse from "@/components/gaoyia-parse/parse.vue";
	//import uParse from "@/components/u-parse/u-parse.vue";
	import httpUtils from '../../common/util/httpUtils.js';
	import momentVideo from '../momentVideo/momentVideo.vue';
	import momentImage from '../momentImage/momentImage.vue';

	export default {

		components: {
			uParse,
			momentVideo,
			momentImage
		},

		data() {
			return {

			};
		},
		props: {
			moment: {
				type: Object,
			},
			momentPageType: {
				type: String
			},
			fromNotification: {
				type: Boolean
			},
			notificationType: {
				type: String
			}
		},

		computed: {
			notificationTypeDesc() {
				if (this.notificationType === "REPOST") {
					return "转发：";
				}
				if (this.notificationType === "AT_ON_COMMENT") {
					return "评论：";
				}
				if (this.notificationType === "AT_ON_CHILDREN_COMMENT") {
					return "评论：";
				}
				return "";
			}
		},

		methods: {
			// 转发
			clickRepost() {
				let repostMomentId;
				if (this.moment.repostMomentId === 0) {
					repostMomentId = this.moment.id;
				} else {
					repostMomentId = this.moment.repostMomentId;
				}
				uni.navigateTo({
					url: `../publishMoment/publishMoment?publishType=REPOST&repostMomentId=${repostMomentId}`
				});
			},
			// 重新编辑被拒绝的提交
			toPublishMoment(momentId) {
				uni.redirectTo({
					url: `/pages/editSubMoment/editSubMoment?publishType=MOMENT&momentId=${momentId}`
				});
			},
			//删除该动态
			async deleteMoment(momentId) {
				uni.showLoading({
					title: "删除中..."
				});
				let [publishMomentData, publishMomentError] =await httpUtils.postJson("/moment/deleteMoment", {
					
					momentId: this.moment.id
				});
				if (!publishMomentError) {
					
					uni.hideLoading();
					uni.redirectTo({
						url: `/pages/myMoment/myMoment?pageType=FRIEND_MOMENT_LIST2&userId=${this.moment.userId}`
						
						//url: `../momentDetail/momentDetail?publishType=MOMENT&momentId=${momentId}`
						//url: `../../pages/momentDetail/momentDetail?momentId=${this.momentId}`
					});
				}
			},

			
			
		}
	}
</script>

<style>
	
	@import url("../gaoyia-parse/parse.css");
	
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
		justify-content: flex-end;
		margin: 3rpx 0 0 0;
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
		align-items: flex-end;
		margin-right: 20rpx;
		border-radius:20rpx;
	}
	
	.moment-operation-like-text-active {
		color: #db301f;
	}
	
	.moment-operation-share {
		margin-right: 25rpx;
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
		width: 45rpx;
		height: 45rpx;
		margin-right: 15rpx;
	}
	
</style>
