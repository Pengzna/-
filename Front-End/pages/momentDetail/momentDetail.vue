<template>
	<view style="height: 100%;">
		<!-- moment信息 -->
		<moment :moment="moment" momentPageType="DETAIL" v-if="moment && moment.id" v-on:updateMomentLikeCount="updateMomentLikeCount($event,moment)"
				 v-on:updateMomentHasLiked="updateMomentHasLiked($event,moment)">
		</moment>

			<!-- 详情页操作区域 -->
						<view class="moment-operation-area" >
							<view class="moment-operation-left">
								<view class="moment-operation-like moment-operation-item" @click.stop="clickLike">
									<image :src="moment.hasLiked ? '../../static/icon/moment-operation-like-active.png' : '../../static/icon/moment-operation-like.png'"
									 class="moment-operation-icon"></image>
									<!-- <text v-if="moment.likeCount === 0" :class="moment.hasLiked ? 'moment-operation-like-text-active' : ''">赞</text> -->
									<text v-if="moment.likeCount > 0" :class="moment.hasLiked ? 'moment-operation-like-text-active' : ''">{{moment.likeCount}}</text>
								</view>
								<view v-if="moment.userId===userId" class="moment-operation-repost moment-operation-item" @click.stop="toPublishMoment(moment.id)">
									<image src="../../static/icon/moment-operation-edit.png" class="moment-operation-icon"></image>
									<!-- <text v-if="moment.repostCount === 0">编辑</text> -->
									
								</view>
								<view v-if="moment.userId===userId" class="moment-operation-comment moment-operation-item" @click.stop="deleteee(moment.id)">
									<image src="../../static/icon/delete.png" class="moment-operation-icon"></image>
									<!-- <text v-if="moment.commentCount === 0">删除</text> -->
									
								</view>
								
								
							</view>
							
						</view>
		<!-- 评论，转发，点赞 -->
		<view class="tab-nav-main-wrapper">
			<view class="tab-nav-main">
				<view class="tab-nav-list" ref="tabNavList">
					<view class="tab-nav-item" :class="curTabIndex==0 ? 'tab-nav-item-active' : ''" :data-index="0" @click="clikeTabNav">
						评论
						<text class="tab-nav-item-num">{{moment.commentCount}}</text>
					</view>
					<view class="tab-nav-item" :class="curTabIndex==1 ? 'tab-nav-item-active' : ''" :data-index="1" @click="clikeTabNav">
						转发
						<text class="tab-nav-item-num">{{moment.repostCount}}</text>
					</view>
					<view class="tab-nav-item" :class="curTabIndex==2 ? 'tab-nav-item-active' : ''" :data-index="2" @click="clikeTabNav">
						赞
						<text class="tab-nav-item-num">{{moment.likeCount}}</text>
					</view>
				</view>
			</view>
		</view>
		<view class="line"></view>
		<swiper class="tab-content" :duration="300" :current="curTabIndex" @change="swiperChange" acceleration="false">
			<swiper-item class="tab-content-item">
				<commentList :commentList="commentInfo.commentList" :parentId="parseInt('0')" title="最新评论"></commentList>
			</swiper-item>
			<swiper-item class="tab-content-item">
				<momentDetailRepost v-for="(repostUser, index) in repostInfo.repostUserList" :repostUser="repostUser" :index="index"></momentDetailRepost>
			</swiper-item>
			<swiper-item class="tab-content-item">
				<momentDetailLike v-for="(likeUser, index) in likeInfo.likeUserList" :likeUser="likeUser" :index="index"></momentDetailLike>
			</swiper-item>
		</swiper>

		<publishComment :parentComment="parentComment" :momentId="moment.id"></publishComment>
	</view>
</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';
	import moment from '../../components/moment/moment.vue';
	import commentList from '../../components/commentList/commentList.vue';
	import publishComment from '../../components/publishComment/publishComment.vue';
	import momentDetailRepost from '../../components/momentDetailRepost/momentDetailRepost.vue';
	import momentDetailLike from '../../components/momentDetailLike/momentDetailLike.vue';
	var _self;//用来在函数中调用另一个函数
	export default {
		created(){
			_self=this
		},
		components: {
			moment,
			commentList,
			publishComment,
			momentDetailRepost,
			momentDetailLike
		},

		data() {
			return {
				// 当前tab索引
				curTabIndex: 0,

				// 发评论
				parentComment: null,

				// moment数据
				momentId: 0,
				moment: {},
				userId:0,
				// comment数据
				commentInfo: {
					commentList: [],
					pageNo: 1,
					pageSize: 20
				},
				// repost数据
				repostInfo: {
					repostUserList: [],
					pageNo: 1,
					pageSize: 20
				},
				// like数据
				likeInfo: {
					likeUserList: [],
					pageNo: 1,
					pageSize: 20
				}
			}
		},

		async onLoad(options) {
			// 判断是否登录
			let loginUser = uni.getStorageSync("loginUser");
			this.userId=loginUser.userId;
			console.log(this.userId);
						
			// let currentPage = this.getCurrentPage(getCurrentPages());
			// let [checkLoginTokenData, checkLoginTokenDataError] = await httpUtils.postJson("/login/checkLoginToken", {},
			// 	currentPage);
			// if (checkLoginTokenDataError === "请重新登录") {
			// 	currentPage = encodeURIComponent(currentPage);
			// 	uni.redirectTo({
			// 		url: `/pages/login/login?fromPage=${currentPage}`
			// 	});
			// 	return;
			// }

			this.momentId = options.momentId;
			uni.showLoading({
				title: '数据加载中...'
			});
			// 加载moment数据
			let [momentDetailData] = await httpUtils.postJson("/moment/getMomentDetail", {
				momentId: options.momentId
			});
			console.log(momentDetailData)
			if(momentDetailData==null){
				uni.navigateBack({
					delta:1,
					success: (res) => {
					 uni.showToast({
					 	title:"帖子不存在啦"
					 })
					},
				})
				

			}
			this.moment = momentDetailData.body;
			//console.log(this.moment.textContent);
			uni.hideLoading();

			await this.loadCommentInfo();
			await this.loadRepostInfo();
			await this.loadLikeInfo();

			// 监听事件
			uni.$on('goToReplyComment', comment => {
				this.parentComment = comment;
				console.log("on goToReplyComment")
			});
			uni.$on('afterPublishComment', data => {
				if (data.parentId) {
					let c = this.commentInfo.commentList.filter(x => x.id === data.parentId);
					c[0].replyCount++;
				} else {
					this.commentInfo.commentList.unshift(data.newComment);
				}

				this.parentComment = null;
				console.log("on afterPublishComment")
			});
			uni.$on('updateCommentLikeCount', event => {
				for (let i in this.commentInfo.commentList) {
					let comment = this.commentInfo.commentList[i];
					if (comment.id === event.id) {
						comment.likeCount = event.likeCount;
						return;
					}
				}
			});
			uni.$on('updateCommentHasLiked', event => {
				for (let i in this.commentInfo.commentList) {
					let comment = this.commentInfo.commentList[i];
					if (comment.id === event.id) {
						comment.hasLiked = event.hasLiked;
						return;
					}
				}
			});
			uni.$on('updateMomentHasFollowed', event => {
				this.moment.hasFollowed = event.hasFollowed;
			});
			uni.$on('updateCommentHasFollowed', event => {
				for (let i in this.commentInfo.commentList) {
					let comment = this.commentInfo.commentList[i];
					if (comment.id === event.id) {
						comment.hasFollowed = event.hasFollowed;
						return;
					}
				}
			});
		},

		async onReachBottom() {
			console.log("momentDetail触底");
			if (this.curTabIndex === 0) {
				await this.loadCommentInfo();
			} else if (this.curTabIndex === 1) {
				await this.loadRepostInfo();
			} else {
				await this.loadLikeInfo();
			}
		},

		async onShareAppMessage(res) {
			await httpUtils.postJson("/share/share", {
				pageType: "MOMENT_DETAIL",
				momentId: this.moment.id
			});

			let loginUser = uni.getStorageSync("loginUser");
			return {
				title: this.moment.userName + "的动态",
				path: `/pages/momentDetail/momentDetail?shareUserId=${loginUser.userId}&momentId=${this.moment.id}`
			}
		},

		methods: {
			// 界面相关函数
			// 点击tab导航
			clikeTabNav(e) {
				let newIndex = e.currentTarget.dataset.index;
				this.updateCurTabIndex(newIndex);
			},
				updateMomentLikeCount(event, moment) {
					moment.likeCount = event;
				},
				
				updateMomentHasLiked(event, moment) {
					moment.hasLiked = event;
				},
				toPublishMoment(momentId) {
					uni.redirectTo({
						url: `../editMoment/editMoment?publishType=MOMENT&momentId=${momentId}`
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
				deleteee(momentId){
					uni.showModal({
						title: '提示',
						content: '是否删除帖子？',
						success: function (res) {
							if (res.confirm) {
								//console.log('用户点击确定');
								_self.deleteMoment(momentId);
							} else if (res.cancel) {
								//console.log('用户点击取消');
							}
						}
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
						uni.reLaunch({
							url: "../square/square",
							//url: `../momentDetail/momentDetail?publishType=MOMENT&momentId=${momentId}`
							//url: `../../pages/momentDetail/momentDetail?momentId=${this.momentId}`
						});
					}
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
				(newIndex !== this.curTabIndex) && (this.curTabIndex = newIndex);
			},

			// 数据相关函数
			// 加载评论数据
			async loadCommentInfo() {
				let [commentListData] = await httpUtils.postJson("/comment/queryMomentComment", {
					momentId: this.momentId,
					parentId: 0,
					pageNo: this.commentInfo.pageNo,
					pageSize: this.commentInfo.pageSize
				});
				this.commentInfo.commentList = this.commentInfo.commentList.concat(commentListData.body);
				this.commentInfo.pageNo++;
			},
			// 加载转发数据
			async loadRepostInfo() {
				let [repostUserData] = await httpUtils.postJson("/repost/queryRepostUser", {
					repostMomentId: this.momentId,
					pageNo: this.repostInfo.pageNo,
					pageSize: this.repostInfo.pageSize
				});
				this.repostInfo.repostUserList = this.repostInfo.repostUserList.concat(repostUserData.body);
				this.repostInfo.pageNo++;
			},
			// 加载点赞数据
			async loadLikeInfo() {
				let [likeUserData] = await httpUtils.postJson("/like/queryLikeUser", {
					targetType: "MOMENT",
					targetId: this.momentId,
					pageNo: this.likeInfo.pageNo,
					pageSize: this.likeInfo.pageSize
				});
				this.likeInfo.likeUserList = this.likeInfo.likeUserList.concat(likeUserData.body);
				this.likeInfo.pageNo++;
			}
		}
	}
</script>

<style>
	.line {
		height: 2rpx;
		background-color: #eeeeee;
		margin-top: -4rpx;
	}

	/* tab导航 */
	.tab-nav-main-wrapper {
		display: flex;
		flex-direction: row;
		justify-content: center;
		border-bottom: 2rpx solid #f3f3f3;
		margin-top: 32rpx;
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
		box-sizing: border-box;
		font-size: 30rpx;
		padding-bottom: 20rpx;
		padding-left: 6rpx;
		padding-right: 6rpx;
		margin: 0 74rpx;
		position: relative;
	}

	.tab-nav-item-num {
		font-size: 20rpx;
		color: #9A9A9A;
		position: absolute;
		right: -20rpx;
		top: 7rpx;
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
	/* 删除图片按钮 */
	.remove-image {
		position: absolute;
		width: 26rpx;
		height: 26rpx;
		background-color: #000000;
		opacity: 0.5;
		top: 6rpx;
		right: 6rpx;
		border-radius: 50%;
		transform: rotate(45deg);
	}
	
	.remove-image-horizontal {
		width: 14rpx;
		height: 2rpx;
		background-color: #ffffff;
		opacity: 1;
		position: absolute;
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
		margin: auto;
	}
	
	.remove-image-vertical {
		width: 2rpx;
		height: 14rpx;
		background-color: #ffffff;
		opacity: 1;
		position: absolute;
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
		margin: auto;
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
