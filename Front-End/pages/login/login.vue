<template>
	<view class="login-root">
		<image class="login-logo" src=""
		 mode="aspectFill"></image>
		<view class="login-button-wrapper">
			<view class="login-wechat-icon">
			</view>
			<!-- #ifdef MP-WEIXIN -->
			<button class="login-button" open-type='getUserInfo' @click="getUserInfo">
				微信快速登录
			</button>
			<!-- #endif -->
			<!-- #ifdef MP-QQ -->
			<button class="login-button" open-type='getUserInfo' @getuserinfo="getUserInfo">
				QQ快速登录
			</button>
			<!-- #endif -->
			
		</view>

		<!-- 弹窗 -->
		<view class="popup-mask" v-if="mastOpened">
		</view>
		<view class="popup-area" v-if="mastOpened">
			<view class="popup-content">
				<view class="popup-title">
					🥑登录ヾ(≧▽≦*)o
				</view>
				<view class="popup-desc">
					账号被禁言或无法登陆请联系客服微信
					【文明发帖哦~】
				</view>
				<view class="popup-button">
					<view class="popup-cancel" @click="cancelPopup">
						取消
					</view>
					<!-- #ifdef MP-WEIXIN -->
					<button class="popup-confirm" open-type='getUserInfo' @click="getUserInfo">
						一起来玩耍
					</button>
					<!-- #endif -->
					<!-- #ifdef MP-QQ -->
					<button class="popup-confirm" open-type='getUserInfo' @getuserinfo="getUserInfo">
						QQ一起来玩耍
					</button>
					<!-- #endif -->
					
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
				mastOpened: true,
				fromPage: "/pages/square/square"
			}
		},

		async onLoad(options) {
			
			console.log("登录页options：", options);
			if (options && options.fromPage) {
				this.fromPage = decodeURIComponent(options.fromPage);
			}
			// #ifdef H5
			this.setLoginUser({
				"artistId": 86643,
				"artistName": "we",
				"userId": 21,
				"userName": "y",
				"userAvatarUrl": "https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoIIvtsffiaKrVeDJZ0ibQ3mzw755VltMjcxTia7tRWrs5jgIUYEGNmyj25OpicqGJYNoZX9oZB1pzzqw/132",
				"unionId": "",
				"token": "eyJhbGciOiJIUzI1NiJ9.eyJ1bmlvbklkIjoiIiwiaXNzIjoibWUuZmFuY2hhb28iLCJhcnRpc3RJZCI6ODY2NDMsImFydGlzdE5hbWUiOiJ3ZSIsInVzZXJOYW1lIjoieSIsImlhdCI6MTYxNTQ1MTYyMzgyNywidXNlcklkIjoyMX0.Ikt42tlgIq7MHawEaBkFSmikwuKe4jKuV8oqmc-XpPY"
			});
			
			uni.switchTab({
				url: '../square/square'
			});
			// #endif
		},

		methods: {
			
			getUserInfo(res) {
				// 登录
				// uni.showLoading({
				// 	title: "正在登录..."
				// });
				if(uni.getUserProfile){
					console.log("微信");
				uni.getProvider({
					service: 'oauth',
					success: (res) => {
						console.log("uni.getProvider：", res);
						this.provider = res.provider[0];
						uni.getUserProfile({
							
							//provider: this.provider,
							desc:"个人使用",
							success: (res1) => {
								console.log("uni.getUserInfo：", res1);
								uni.login({
									provider: this.provider,
									
									success: (res) => {
										console.log("uni.login0：", res);
										console.log("uni.login1：", res1);
										this.code = res.code;
										//getUserProfile用来获取个人信息：头像、用户名；getUserInfo用来获取code，code可以在后端得到openid
										uni.getUserInfo({
											provider: this.provider,
											success:(res)=>{
												console.log("uni.getUserInfo22：", res);
												this.login(this.code, res1.userInfo, res.encryptedData, res.iv);
											}
										})
										
										
										// uni.getUserInfo({
										// 	provider: this.provider,
										// 	success:(res)=>{
										// 		console.log("uni.getUserInfo22：", res);
												
										// 	}
										// })
										
									}
								});
							}
						});
						
						
						
					}
				});
				}else{
					// 登录
					uni.showLoading({
						title: "正在登录..."
					});
					
					uni.getProvider({
						service: 'oauth',
						success: (res) => {
							console.log("uni.getProvider：", res);
							this.provider = res.provider[0];
							uni.login({
								provider: this.provider,
								success: (res) => {
									console.log("uni.login：", res);
									this.code = res.code;
									uni.getUserInfo({
										provider: this.provider,
										success: (res) => {
											console.log("uni.getUserInfo：", res);
											this.login(this.code, res.userInfo, res.encryptedData, res.iv);
										}
									})
								}
							});
						}
					});
				}
				
			},

			login(code, userInfo, encryptedData, iv) {
				uni.request({
					url: getApp().globalData.domain + "/login/login",
					method: "POST",
					data: {
						randomKey: getApp().globalData.randomKey,
						provider: this.provider,
						code,
						userInfo,
						encryptedData,
						iv
					},
					dataType: "json",
					success: (res) => {
						
						if (res.statusCode !== 200) {
							uni.showToast({
								title: "服务器内部错误",
								duration: 500,
							});
							return;
						}
						if (res.data.retcode != 0) {
							uni.showToast({
								title: "可能是账号异常" + res.data.retcode,
								duration: 500,
							});
							return;
						}
						this.setLoginUser(res.data.body);
						uni.hideLoading();
						console.log("跳转到：", this.fromPage);
						if (this.fromPage === "/pages/index/index" || this.fromPage === "/pages/square/square") {
							uni.switchTab({
								url: "/pages/universityList/universityList"
							});
						} else {
							uni.redirectTo({
								url: "/pages/universityList/universityList"
							});
						}

					}
				});
			},

			cancelPopup() {
				this.mastOpened = false;
			}
		}
	}
</script>

<style>
	.login-root {
		width: 100%;
		height: 100%;
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.login-logo {
		width: 200rpx;
		height: 200rpx;
		border-radius: 50%;
		margin-top: 76rpx;
	}

	.login-button-wrapper {
		position: relative;
		width: 560rpx;
		height: 100rpx;
		margin-top: 250rpx;
		background-color: #42b133;
		border-radius: 40rpx;
		border: none;
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
	}

	.login-wechat-icon {
		position: absolute;
		left: 106rpx;
		top: 10rpx;
		width: 80rpx;
		height: 80rpx;
		background: url('../../static/icon/wechat-logo.png') no-repeat;
		background-size: 100% 100%;
	}

	.login-button {
		width: 560rpx;
		height: 100rpx;
		padding-left: 70rpx;
		font-size: 34rpx;
		line-height: 100rpx;
		background-color: transparent;
		color: white;
		border: none;
	}

	button:after {
		border: none;
	}

	/* 弹窗 */
	.popup-mask {
		position: absolute;
		left: 0;
		top: 0;
		width: 100%;
		height: 100%;
		background-color: #000000;
		opacity: 0.39;
	}

	.popup-area {
		position: absolute;
		left: 0;
		top: 0;
		width: 100%;
		height: 100%;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}

	.popup-content {
		position: relative;
		background-color: #ffffff;
		width: 602rpx;
		border-radius: 12rpx;
	}

	.popup-title {
		font-size: 32rpx;
		font-weight: bold;
		height: 130rpx;
		box-sizing: border-box;
		text-align: center;
		padding-top: 60rpx;
		color: #121212;
	}

	.popup-desc {
		margin-top: 10rpx;
		text-align: center;
		font-size: 32rpx;
		padding-bottom: 172rpx;
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
		background-color: white;
	}

	.popup-cancel:active,
	.popup-confirm:active {
		background-color: #f0f0f0;
	}
</style>
