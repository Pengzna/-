<template>
	<view  class="moment-root" :class="momentPageType === 'LIST' ? 'moment-root-border' : '' " @click.stop=" goToMomentDetail(moment.id)">
	
	
	
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
						<view class="moment-user-name" @click.stop="loaddifference(moment.userId)">
							{{moment.userName}}
						</view>
						<view class="moment-notification-type">
							{{notificationTypeDesc}}
						</view>
					</view>
					<view class="moment-create-time" :class="fromNotification ? 'moment-create-time-right' : '' ">
						<!-- {{moment.createTime | chineseTimeFormat}} -->
						<view v-if="moment.userId>100000000&&!fromNotification" class="moment-create-time-u" :class="fromNotification ? 'moment-create-time-right' : '' ">
						{{moment.university}} 
					</view>
					{{moment.createTime| chineseTimeFormat}}
					</view>
				</view>
			</view>

			<!-- 	<view class="uni-title uni-common-mt">
				                
				                <text>字符串类型\nnodes属性为String</text>
				            </view> -->
			<!-- 动态内容 -->
			<view class="moment-text-content" :class="fromNotification ? 'moment-text-content-no-margin-top' : '' " >
				<!-- <u-parse v-if="moment.textContent" :content="moment.textContent"></u-parse> -->
				<!-- <textarea v-if="moment.textContent" :value="moment.textContent"/> -->
				<!-- <mp-html container-style="white-space:pre-wrap" :content="moment.textContent" /> -->
				<text>{{moment.textContent}}</text>
				<!-- <text data-type='USER' style='color: #577C9F'>@我</text> -->
			</view>

			<!-- 转发区域 -->
			<view class="moment-repost-area" v-if="moment.repostMomentId !== 0&&moment.repostMomentId!==null" @click.stop="goToMomentDetail(moment.repostMomentId)">
				<view class="moment-repost-user-text-content">
					<text class="moment-repost-user-name" v-if="moment.repostedMoment.userName!=null">
						@{{moment.repostedMoment.userName}}
					</text>
					<text class="moment-repost-user-name" v-if="moment.repostedMoment.userName==null">
						@原贴已删除啦
					</text>
					<text class="moment-repost-text-content" v-if="moment.repostedMoment.textContent!=null">
						{{moment.repostedMoment.textContent}}
					</text>
					
					<text class="moment-repost-text-colon">：</text>
					
					<text class="moment-repost-text-content">
						<u-parse v-if="moment.repostedMoment.textContent" :content="moment.repostedMoment.textContent"></u-parse>
					
					</text>
				</view>
				<!-- 转发视频区域 -->
				<view class="moment-video-area" v-if="moment.type === 'VIDEO'" @click.stop="goToMomentDetail(moment.repostedMoment.id)">
					<momentVideo :moment="moment.repostedMoment"></momentVideo>
				</view>
				<!-- 转发图片区域 -->
				<view class="moment-image-area" v-if="moment.type === 'IMAGE' ">
					<momentImage :moment="moment.repostedMoment"></momentImage>
				</view>
			</view>
			<!-- 视频区域 -->
			<view class="moment-video-area" v-if="moment.repostMomentId === 0 && moment.type === 'VIDEO'" @click.stop="momentPageType === 'LIST' && goToMomentDetail(moment.id) || momentPageType === 'DETAIL' && goToVideoDetail(moment.id)">
				<momentVideo :moment="moment"></momentVideo>
			</view>
			<!-- 图片区域 -->
			<view class="moment-image-area" v-if="moment.repostMomentId === 0 && moment.type === 'IMAGE' ">
				<momentImage :moment="moment"></momentImage>
			</view>

<!-- <view class="moment-operation-area" v-if="moment.userId===userId&&momentPageType==='DETAIL'">
<button class="moment-operation-share moment-operation-item"  @click.stop="toPublishMoment(moment.id)">
					<image src="../../static/icon/moment-operation-sharee.png" class="moment-operation-icon"></image>
				</button>
			</view> -->

			<!-- 操作区域 -->
			<view class="moment-operation-area" v-if="momentPageType==='LIST'&&!fromNotification">
				<view class="moment-operation-left">
					<view class="moment-operation-repost moment-operation-item" @click.stop="clickRepost">
						<image src="../../static/icon/moment-operation-repost.png" class="moment-operation-icon"></image>
						<text v-if="moment.repostCount === 0">转发</text>
						<text v-if="moment.repostCount > 0">{{moment.repostCount}}</text>
					</view>
					<view class="moment-operation-comment moment-operation-item" @click.stop="goToMomentDetail(moment.id)">
						<image src="../../static/icon/moment-operation-comment.png" class="moment-operation-icon"></image>
						<text v-if="moment.commentCount === 0">评论</text>
						<text v-if="moment.commentCount > 0">{{moment.commentCount}}</text>
					</view>
					<view class="moment-operation-like moment-operation-item" @click.stop="clickLike">
						<image :src="moment.hasLiked ? '../../static/icon/moment-operation-like-active.png' : '../../static/icon/moment-operation-like.png'"
						 class="moment-operation-icon"></image>
						<text v-if="moment.likeCount === 0" :class="moment.hasLiked ? 'moment-operation-like-text-active' : ''">赞</text>
						<text v-if="moment.likeCount > 0" :class="moment.hasLiked ? 'moment-operation-like-text-active' : ''">{{moment.likeCount}}</text>
					</view>
					
				</view>
				<button  class="moment-operation-share moment-operation-item" open-type="share" @click.stop="clickShare">
					<image src="../../static/icon/moment-operation-share.png" class="moment-operation-icon"></image>
				</button>
			</view>

		</view>
		
		</view>
</template>

<script>
	import mpHtml from '@/uni_modules/mp-html/components/mp-html/mp-html';
	import uParse from "@/components/gaoyia-parse/parse.vue";
	//import uParse from "@/components/u-parse/u-parse.vue";
	import httpUtils from '../../common/util/httpUtils.js';
	import momentVideo from '../momentVideo/momentVideo.vue';
	import momentImage from '../momentImage/momentImage.vue';

	export default {

		components: {
			uParse,
			momentVideo,
			momentImage,
			mpHtml
		},

		data() {
			return {

			};
		},
		filters: {
			// 时间戳处理
			formatDate: function(value, spe = '-') {
			let time = Math.floor(((new Date()).valueOf() - value) / 86400000)
			if (time === 0) {
			        let time2 = Math.round(((new Date()).valueOf() - value) / 3600000)
				if (time2 > 0) {
				        return time2 + '小时前'
				} else {
					let time3=((new Date()).valueOf() - value) / 60000
					if(time3>1){
						return Math.ceil(time3) + '分钟前'
					}else{
						return Math.ceil(((new Date()).valueOf() - value)/1000)+"秒前"
					}					
				}
			} else if (time > 0 && time < 30) {
				return time + '天前'
				} else if (time >= 30) {
					let data = new Date(value);
					let year = data.getFullYear();
					let month = data.getMonth() + 1;
					let day = data.getDate();
					let h = data.getHours();
					let mm = data.getMinutes();
					let s = data.getSeconds();
					month = month > 10 ? month : "0" + month;
					day = day > 10 ? day : "0" + day;
					return `${year}${spe}${month}${spe}${day}`;
				}
			},
		},
		async onLoad(){
			console.log(moment);
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
			goToMomentDetail(e) {
				//let repostMomentId=e.repostMomentId;
				
				if(this.momentPageType == 'LIST'){
					uni.navigateTo({
						url: `/pages/momentDetail/momentDetail?momentId=${e}`
					});
				}
				
			},
							//删除该动态
			async deleteMoment(momentId) {
				await httpUtils.postJson("/moment/deleteMoment", {
					
					momentId: this.moment.id
				});
			},
			goToVideoDetail(momentId) {
				uni.navigateTo({
					url: `../../pages/videoDetail/videoDetail?momentId=${momentId}`
				});
			},
			loaddifference(e) {
				
					
					uni.navigateTo({
						url: `/pages/momentList/momentList?userId=${e}`
					});
				
				
			}
		}
	}
</script>

<style>
	@import url("moment.css");
	@import url("../gaoyia-parse/parse.css");
</style>
