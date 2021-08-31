<template>
	<view>
	<view class="moment-root" :class="momentPageType === 'LIST' ? 'moment-root-border' : '' " >
		
		<!-- <view class="cu-form-group margin-top">
			<checkbox-group @change="checkboxChange">
			<checkbox   :checked="false" value="A" @click="click(value)"></checkbox>
		</checkbox-group>
		</view> -->
		
		<!-- 头像 -->
		<image class="moment-avatar" :class="momentPageType === 'LIST' ? 'moment-avatar-list-margin' : '' " :src="moment.userAvatarUrl"
		 mode="aspectFill" v-if="momentPageType === 'LIST'" @click.stop="clickUser('MOMENT',moment)"></image>

		<view :class="momentPageType === 'LIST' ? 'moment-list-content' : 'moment-detail-content' ">

			<view class="moment-user-info">
				<!-- 头像 -->
				<image class="moment-avatar" :class="momentPageType === 'DETAIL' ? 'moment-avatar-detail-margin' : '' " :src="moment.userAvatarUrl"
				 mode="aspectFill" v-if="momentPageType === 'DETAIL' " @click.stop="clickUser('MOMENT',moment)"></image>

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
			<!-- <view class="moment-01" >	审核状态展示，样式使用colorui	
					<view  class="cu-tag round bg-orange sm" >
				<text v-if="moment.activityId === 1" >待审核</text>
				<text v-if="moment.activityId === 2" >已发布</text>
				<text v-if="moment.activityId === 3" >已拒绝</text>
				<text v-if="moment.activityId === 0" >无须操作</text>
				
				</view>
			
			</view> -->
			<!-- 动态内容 -->
			<view class="moment-text-content" :class="fromNotification ? 'moment-text-content-no-margin-top' : '' ">
				<u-parse v-if="moment.textContent" :content="moment.textContent"></u-parse>
			</view>
			

			
				<!-- <view class="moment-image-area" v-if="moment.type === 'IMAGE' ">
					<momentImage :moment="moment.repostedMoment"></momentImage>
				</view> -->
				<!-- 图片区域 -->
				<view class="moment-image-area" v-if="moment.repostMomentId === 0 && moment.type === 'IMAGE' ">
					<momentImage :moment="moment"></momentImage>
				</view>
			</view>

			
			
			

		</view>
		
		<view>
			
		<view class="moment-operation-area" >
		
						<view class="moment-operation-share moment-operation-item"  @click.stop="refuse">
											<image src="../../static/icon/delete.png" class="moment-operation-icon"></image>
											<text >拒绝</text>
										</view>
					</view>
		</view>
		<!-- 弹窗 -->
		<uni-popup ref="popup" type="center">
			<view class="popup-content">
				<view class="popup-title">
					{{currentFieldDesc}}
				</view>
				<input ref="inputValue" :focus="inputFocus" class="popup-desc" type="text" v-model="currentFieldValue"  />
				
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
</template>

<script>
	import uParse from "@/components/gaoyia-parse/parse.vue";
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
			imageTempList: [
									],
			currentFieldName: "REFUSE",
			currentFieldDesc: "填写拒绝理由",
			currentFieldValue: "",
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
			checkboxChange: function (e) {
				console.log(e.detail.checked);
				if(e.detail.value=="A"){
					e.detail.value="B";
				}
				if(e.detail.value=="B"){
				this.imageTempList.push({
									path: this.moment.id,
									
								});
								}
				console.log(this.imageTempList);
			                // var items = this.items,
			                //     values = e.detail.value;
			                // for (var i = 0, lenI = items.length; i < lenI; ++i) {
			                //     const item = items[i]
			                //     if(values.includes(item.value)){
			                //         this.$set(item,'checked',true)
			                //     }else{
			                //         this.$set(item,'checked',false)
			                //     }
			                // }
			            },
				click(value){
				console.log(value);	
				this.imageTempList.push({
									path: this.moment.id,
									isChecked:true,
								});
								console.log(this.imageTempList);	
				},
			async confirmPopup() {
				this.moment.textContent=this.currentFieldValue+"  】"+this.moment.textContent;
				await httpUtils.postJson("/moment/refuseMoment", {
					textContent: this.moment.textContent,
					momentId:this.moment.id
				});
				//await this.loadUserInfo();
				uni.redirectTo({
					url: `/pages/verify/verify`
				});
				this.$refs.popup.close();
			},
	
			// 点赞
			async clickLike() {
				if (this.moment.hasLiked) {
					await httpUtils.postJson("/like/cancelLike", {
						targetType: 'MOMENT',
						targetId: this.moment.id
					});
					this.moment.likeCount--;
				} else {
					await httpUtils.postJson("/like/like", {
						targetType: 'MOMENT',
						targetId: this.moment.id
					});
					this.moment.likeCount++;
				}
				this.moment.hasLiked = !this.moment.hasLiked;
				this.$emit('updateMomentLikeCount', this.moment.likeCount);
				this.$emit('updateMomentHasLiked', this.moment.hasLiked);

				console.log(this.moment.likeCount, this.moment.hasLiked);
			},

			// 分享
			async clickShare(e) {
				// console.log(e)
				// await httpUtils.postJson("/share/share", {
				// 	pageType: "MOMENT_DETAIL",
				// 	momentId: this.moment.id
				// });
				// this.moment.shareCount++;
			},
			refuse(){
				this.$refs.popup.open();
			},
			cancelPopup() {
				
				this.$refs.popup.close();
			},
			goToMomentDetail(momentId) {
				uni.navigateTo({
					url: `../../pages/momentDetail/momentDetail?momentId=${momentId}`
				});
			},
			goToVideoDetail(momentId) {
				uni.navigateTo({
					url: `../../pages/videoDetail/videoDetail?momentId=${momentId}`
				});
			}
		}
	}
</script>

<style>
	@import url("verifymoment.css");
	@import url("../gaoyia-parse/parse.css");
	
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
	
	.gender-input {}
	
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
		border-radius:5rpx;
	}
	
	.moment-operation-like-text-active {
		color: #db301f;
	}
	
	.moment-operation-share {
		margin-right: 25rpx;
		background-color: #FFFFFF;
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
	.moment-01 {
		margin-left: 0;
	}
</style>
