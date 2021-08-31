<template>
	<view>
		
	<view class="feedback-root">

		

		<!-- 内容 -->
		<view class="feedback-content-wrapper common-padding feedback-item-margin-top">
			<view class="feedback-content-title">
				请输入学校+姓名 并上传学生证
			</view>
			<textarea class="feedback-content" v-model="content" required placeholder-class="feedback-content-placeholder" value=""
			 placeholder="1.请将学生证置于电脑上拍照(确认是本人的信息,而非盗图)\n 2.学生证照片部分可打码（保护个人隐私）" />
			 
			 <!-- 图片区域 -->
			 <view class="image-area" >
			 	<view class="image-wrapper" v-for="(tempImage, index) in imageTempList">
			 		<image class="image" mode="aspectFill" :src="tempImage.path">
			 		</image>
			 		<view class="remove-image" @click="removeImage" :data-image-index="index">
			 			<view class="remove-image-horizontal">
			 			</view>
			 			<view class="remove-image-vertical">
			 			</view>
			 		</view>
			 	</view>
			 	
				
			 	<view class="add-image" @click="chooseImage">
			 		<view class="add-image-horizontal">
			 		</view>
			 		<view class="add-image-vertical">
			 		</view>
			 	</view>
			 </view>
			</view>
		
		
		
		
		<view class="feedback-other-wrapper common-padding feedback-item-margin-top">
			<view class="feedback-other-title">
				联系方式（选填）
			</view>
			<input v-model="contactInfo" placeholder-class="feedback-content-placeholder" type="text" value="" placeholder="您的联系方式" />
		</view>
		
		<view class="feedback-submit" @click="openPopup">
			提交
		</view>
		
		<!-- 弹窗 -->
		<uni-popup ref="popup" type="center">
			<view class="popup-content">
				<view class="popup-title">
					学生认证
				</view>
				<view class="popup-desc">
					是否确认提交
				</view>
				<view class="popup-button">
					<view class="popup-cancel" @click="cancelPopup">
						取消
					</view>
					<view class="popup-confirm"  @click="confirmPopup">
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
	import uniPopup from '@/components/uni-popup/uni-popup.vue';
	
	export default {
		comments:{
			uniPopup
		},
		
		data() {
			return {
				type:"OTHER",
				content:"",
				contactInfo:"",
				// 临时图片列表
				imageTempList: [
									],
				// 图片列表
				imageList: [],
			}
		},
		methods: {
			// 打开弹层
			openPopup() {
				this.$refs.popup.open();
			},
			
			cancelPopup(){
				this.$refs.popup.close();
			},
			// 选择图片
			chooseImage() {
				uni.chooseImage({
					count: 1,
					sizeType: ['original'],
					sourceType: ['album'],
					success: async (res) => {
						if (this.imageTempList.length + res.tempFilePaths.length > 1) {
							uni.showToast({
								title: "最多上传1张图片",
								duration: 2000
							})
							return;
						}
			
						for (let i in res.tempFilePaths) {
							let tempFilePath = res.tempFilePaths[i];
			
							let [error, imageInfo] = await uni.getImageInfo({
								src: tempFilePath
							});
							this.imageTempList.push({
								path: tempFilePath,
								width: imageInfo.width,
								height: imageInfo.height
							});
						}
					}
				});
			},
			
			// 移除图片
			removeImage(e) {
				let imageIndex = e.currentTarget.dataset.imageIndex;
				this.imageTempList.splice(imageIndex, 1);
			},
			
			async confirmPopup(){
				this.$refs.popup.close();
				await this.addFeedback();
			},
			
			async addFeedback(){
				if (!this.type) {
					uni.showToast({
						title: "类型不能为空",
						duration: 2000
					});
					return;
				}
				
				if (!this.content) {
					uni.showToast({
						title: "内容不能为空",
						duration: 2000
					});
					return;
				}
				
				// await httpUtils.postJson("/setting/addFeedback", {
				// 	type:this.type,
				// 	content:this.content,
				// 	contactInfo:this.contactInfo
				// });
				
				
				
				for (let i in this.imageTempList) {
					
					let imageTemp = this.imageTempList[i];
					let [url] = await httpUtils.uploadFile(imageTemp.path);
					console.log("已上传：" + url);
					this.imageList.push({
						url,
						width: imageTemp.width,
						height: imageTemp.height
					});
				}
				
				let param = {
					
				};
				
				param.imageList = this.imageList;
				
				
				//let [publishMomentData, publishMomentError] = await httpUtils.postJson("/moment/publishMoment", param);
				
				await httpUtils.postJson("/setting/addFeedback", {
					type:this.type,
					content:this.content,
					contactInfo:param.imageList[0].url
				});
				
				
				uni.showToast({
					title: "提交成功",
					duration: 10000,
					mask:true,
					success() {
						setTimeout(uni.navigateBack,1000);
					}
				});
			}

		}
	}
</script>

<style>
	.feedback-root{
		background-color: #F2F3F7;
		height: 100%;
		font-size: 32rpx;
		color: #0B0B0B;
	}
	
	.feedback-item-margin-top{
		margin-top: 16rpx;
		background-color: white;
	}
	
	.feedback-type-wrapper{
		padding-bottom: 40rpx;
	}
	.feedback-type{
		font-size: 30rpx;
		color: #3D3D3D;
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		margin-top: 30rpx;
	}
	.feedback-type-radio{
		margin-right: 20rpx;
	}
	.feedback-type-radio radio{
		transform:scale(0.7)
	}
	
	.feedback-content-wrapper{
		padding-top: 30rpx;
	}
	.feedback-content-title{
		font-size: 36rpx;
		color: #3D3D3D;
		padding-bottom: 10rpx;
	}
	.feedback-content{
		width: 100%;
		height: 160rpx;
		font-size: 30rpx;
		line-height: 48rpx;
		letter-spacing: 1rpx;
	}
	.feedback-content-placeholder{
		color: #959595;
	}
	
	.feedback-other-wrapper{
		font-size: 30rpx;
		padding-top: 30rpx;
		padding-bottom: 30rpx;
		display: flex;
		flex-direction: row;
	}
	
	.feedback-other-title{
		width: 240rpx;
	}
	.feedback-other-wrapper input{
		width: 450rpx
	}
	
	.feedback-submit{
		font-size: 34rpx;
		display: flex;
		justify-content: center;
		align-items: center;
		color: white;
		margin: 80rpx 0;
		width: 750rpx;
		height: 100rpx;
		background-color: #0fb700;
	}
	
	/* 弹窗 */
	.popup-content{
		position: relative;
		background-color: #ffffff;
		width: 602rpx;
		border-radius: 12rpx;
	}
	
	.popup-title{
		font-size: 30rpx;
		font-weight: bold;
		height: 130rpx;
		box-sizing: border-box;
		text-align: center;
		padding-top: 60rpx;
		color: #121212;
	}
	
	.popup-desc{
		font-size: 32rpx;
		padding-bottom: 188rpx;
		padding-left: 50rpx;
		padding-right: 50rpx;
		display: flex;
		flex-direction: row;
		justify-content: center;
		color: #1C1C1C;
	}
	
	.popup-button{
		position: absolute;
		bottom: 0;
		left: 0;
		display: flex;
		flex-direction: row;
		border-top: 2rpx solid #F1F1F1;
	}
	.popup-cancel{
		font-size: 32rpx;
		width: 300rpx;
		height: 110rpx;
		line-height: 110rpx;
		text-align: center;
		border-right: 2rpx solid #F1F1F1;
		color: #0B0B0B;
		font-weight: bold;
	}
	
	.popup-confirm{
		font-size: 32rpx;
		width: 300rpx;
		height: 110rpx;
		line-height: 110rpx;
		text-align: center;
		color: #f6423d;
		font-weight: bold;
	}
	.popup-cancel:active,.popup-confirm:active{
		background-color: #f0f0f0;
	}
/* 图片区域 */
	.image-area {
		padding: 30rpx 30rpx;
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
	}

	.image-wrapper {
		position: relative;
		width: 216rpx;
		height: 216rpx;
		margin-bottom: 22rpx;
		margin-right: 14rpx;
	}

	.image {
		width: 216rpx;
		height: 216rpx;
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

	/* 添加图片按钮 */
	.add-image {
		position: relative;
		width: 216rpx;
		height: 216rpx;
		background-color: #f7f7f7;
		margin-bottom: 22rpx;
		margin-right: 14rpx;
		transition: background-color 0.3s;
	}

	.add-image:active {
		background-color: #d7d7d7;
	}

	.add-image-horizontal {
		width: 60rpx;
		height: 4rpx;
		background-color: #aeaeae;
		position: absolute;
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
		margin: auto;
		transition: background-color 0.5s;
	}

	.add-image-vertical {
		width: 4rpx;
		height: 60rpx;
		background-color: #aeaeae;
		position: absolute;
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
		margin: auto;
		transition: background-color 0.5s;
	}

	.add-image-horizontal:active {
		background-color: #7b7b7b;
	}

	.add-image-vertical:active {
		background-color: #7b7b7b;
	}


	/* 图片，@，话题，标签等编辑信息 */
	.edit-info {

		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: space-between;
		height: 82rpx;
		padding: 0 30rpx;
		border-top: 2rpx solid #EAEAEA;
		border-bottom: 2rpx solid #EAEAEA;
	}

	.edit-info-image-wrapper {
		display: flex;
		flex-direction: row;
	}

	.edit-info-image-view {
		width: 66rpx;
		height: 66rpx;
		margin-right: 10rpx;
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
	}

	.edit-info-image-view:active {
		background-color: #fbfbfb;
	}

	.edit-info-image {
		width: 40rpx;
		height: 40rpx;
	}

	.edit-info-publish-wrapper {
		display: flex;
		flex-direction: row;
		height: 90rpx;
		font-size: 26rpx;
		line-height: 90rpx;
	}

	.edit-info-publish-word-count {
		color: #9C9C9C;
		margin-right: 16rpx;
	}

	.edit-info-publish-button {
		width: 126rpx;
		border-radius: 34rpx;
		background-color: #f6423d;
		text-align: center;
		color: #ffffff;
		transition: background-color 0.2;
	}

	.edit-info-publish-button:active {
		background-color: #fa0021;
	}
</style>
