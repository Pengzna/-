<template>
	<view>
		<!-- <form>
			<view class="oneRow">
				<text class="textbeforeinput">标题：</text><uni-easyinput v-model="activityInfo.title" placeholder="请输入活动标题" style="float: left;"></uni-easyinput>
			</view>
			
			<view class="oneRow">
				<text class="textbeforeinput">地址：</text><uni-easyinput v-model="activityInfo.address" placeholder="请输入活动地址" style="float: left;"></uni-easyinput>
			</view> -->
			<view class="cu-form-group">
				<view class="title">标题</view>
				<input placeholder="活动的标题" name="input" v-model="textTitle"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">地址</view>
				<input placeholder="本次活动具体地址" name="input"v-model="textAddress"></input>
			</view>
			<view class="oneRow">
				<text class="textbeforeinput">时间：</text>
				<uni-section :title="'日期时间用法：' + datetime" type="line"></uni-section>
				<view class="example-body">
					<uni-datetime-picker type="datetime" v-model="datetime" @change="changeLog" />
				</view>
			</view>
			<!-- <view class="cu-form-group">
				<view class="title">活动详情</view>
				<input placeholder="本次活动详情内容" name="input"></input>
			</view> -->
			<!-- <view class="cu-form-group align-start">
				<view class="title">活动详情</view>
				<textarea  maxlength="-1" :disabled="modalName!=null" @input="textareaBInput" placeholder="本次活动详情内容"v-model="textContent"></textarea>
			</view> -->

			<view class="cu-form-group margin-top">
				<view class="title">活动标签</view>
				<picker @change="PickerChange" :value="index" :range="picker">
					<view class="picker">
						{{index>-1?picker[index]:'选择活动类别'}}
					</view>
				</picker>
			</view>
			<view class="cu-bar bg-white margin-top">
				<view class="action">
					图片上传
				</view>
				<view class="action">
					{{imgList.length}}/1
				</view>
			</view>
			<view class="cu-form-group">
				<view class="grid col-4 grid-square flex-sub">
					<view class="bg-img" v-for="(item,index) in imgList" :key="index" @tap="ViewImage" :data-url="imgList[index]">
					 <image :src="item.path" mode="aspectFill"></image>
						<view class="cu-tag bg-red" @tap.stop="removeImage" :data-index="index">
							<text class='cuIcon-close'></text>
						</view>
					</view>
					<view class="solids" @tap="ChooseImage" v-if="imgList.length<4">
						<text class='cuIcon-cameraadd'></text>
					</view>
				</view>
			</view>
			
			<view class="oneRow">
				<button type="default" @click="publish">提交审核</button>
			</view>
			<!-- <view class="oneRow">
				<text class="textbeforeinput">时间：</text>
				<uni-section :title="'日期时间用法：' + datetime" type="line"></uni-section>
				<view class="example-body">
					<uni-datetime-picker type="datetime" v-model="activityInfo.datetime" @change="changeLog" />
				</view>
			</view> -->
			
			<!-- <view class="oneRow">
				<text class="textbeforeinput">结束时间：</text>
				<uni-section :title="'日期时间用法：' + datetimeend" type="line"></uni-section>
				<view class="example-body">
					<uni-datetime-picker type="datetime" v-model="datetimeend" @change="changeLog" />
				</view>
			</view> -->
			
			
			
			<!-- <view class="oneRow">
				<text class="textbeforeinput">上传图片：</text> <image src="../../static/icon/imgUpload.png" style="width: 105rpx; height: 87rpx;" @click="chooseImage"></image>
				
			</view> -->



			<!-- 
			<view class="oneRow">
				<text class="textbeforeinput">活动详情：</text><uni-easyinput v-model="activityInfo.detailUrl" placeholder="请输入活动详情网页地址" style="float: left;"></uni-easyinput>
			</view>
				
			<view class="oneRow">
				<text class="textbeforeinput">活动标签：</text>
				
				<label style="margin-top: 15rpx;">
					<checkbox value="文体"  v-model="chosenLabels" style="transform:scale(0.7)" /><text style="font-size: small;">文体</text>
				</label>
				<label style="margin-top: 15rpx;">
					<checkbox value="公益"  v-model="chosenLabels" style="transform:scale(0.7)" /><text style="font-size: small;">公益</text>
				</label>
				<label style="margin-top: 15rpx;">
					<checkbox value="讲座"  v-model="chosenLabels" style="transform:scale(0.7)" /><text style="font-size: small;">讲座</text>
				</label>
				<label style="margin-top: 15rpx;">
					<checkbox value="招新"  v-model="chosenLabels" style="transform:scale(0.7)" /><text style="font-size: small;">招新</text>
				</label>
				
			</view>
			
			<view class="oneRow">
				<button type="default" @click="submit">提交审核</button>
			</view>
			
		</form> -->
		
	</view>
</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';
	import uploadImage from '@/common/ossutil/uploadFile.js';

	 export default {
	        data() {
	            return {
					time: '12:01',
					date: '2021-09-25 ',
					index: -1,
	                single: '2021-04-3',
	                range: ['2021-03-8', '2021-4-20'],
	                datetimerange: ['2021-03-20 20:10:10', '2021-05-10 10:10:10'],
					
					//用户填写的数据
					activityInfo:{
						title:"",
						address:"",
						detailUrl:"",
						datetime: '2021-04-3 19:00:00',
						imgUrl:"",
						chosenLabels:[],
					},
					// 临时图片列表
					imageTempList: [
										],
					// 图片列表
					imageList: [],
					
					imgList: [],
					picker: ['文体', '公益', '讲座','招新'],
					
					textTitle:"",
					textAddress:"",
					textContent:"",
					datetime: '2021-04-3 19:00:00',
	            }
	        },
	
	        watch: {
	            datetimesingle(newval) {
	                console.log('单选:', this.datetimesingle);
	            },
	            range(newval) {
	                console.log('范围选:', this.range);
	            },
	            datetimerange(newval) {
	                console.log('范围选:', this.datetimerange);
	            }
	        },
	        mounted() {
	            setTimeout(() => {
	                this.datetimesingle = '2021-5-1'
	                this.single = '2021-5-1'
	            },1000)
	        },
	
	        methods:{
				PickerChange(e) {
					this.index = e.detail.value
				},
				TimeChange(e) {
					this.time = e.detail.value
				},
				DateChange(e) {
					this.date = e.detail.value
				},
	            changeLog(e) {
	                this.single = e
	                console.log('-change事件:', e);
	            },
				//将用户填写的信息传送到数据库
				submit(){
					
				},
				// uploadImg(){
				// 	uni.chooseImage({
				// 		success: (res) => {
				// 			let file = res.tempFiles[0]; //获取文件
				// 			let filePath = res.tempFilePaths[0]; //获取文件地址
				// 			this.activityInfo.imgUrl = filePath; //存入数据区
							
				// 		}
				// 	})
				// }
				// 移除图片
				removeImage(e) {
					let imageIndex = e.currentTarget.dataset.imageIndex;
					this.imgList.splice(imageIndex, 1);
				},
				// 选择图片
				ChooseImage() {
					uni.chooseImage({
						count: 1,
						sizeType: ['original'],
						sourceType: ['album'],
						success: async (res) => {
							if (this.imgList.length + res.tempFilePaths.length > 1) {
								uni.showToast({
									title: "最多提交1张图片",
									duration: 2000
								})
								return;
							}
				
							for (let i in res.tempFilePaths) {
								let tempFilePath = res.tempFilePaths[i];
				
								let [error, imageInfo] = await uni.getImageInfo({
									src: tempFilePath
								});
								this.imgList.push({
									path: tempFilePath,
									width: imageInfo.width,
									height: imageInfo.height
								});
							}
						}
					});
				},
				textareaBInput(e) {
					this.textContent = e.detail.value
				},
				getImageList(){
					for (let i in this.imgList) {
						let imageTemp = this.imgList[i];
						
						let url=uploadImage(imageTemp.path, 'images/',this.getLoginUser().userId);
						
						this.imageList.push({
							url,
							width: imageTemp.width,
							height: imageTemp.height
						});
					}
				},
				async publish() {
					if(this.textTitle.length==0||this.textAddress.length==0||this.index<0){
						uni.showToast({
							title: "必填项不可为空",
							duration: 2000,
						});
					}else{
						// if((this.uniinfo[this.index])){
						// 	uni.showLoading({
						// 		title: "提交中..."
						// 	});
						
							await this.getImageList();
								let param = {
									//momentType: this.momentType,
									//textContent: this.textContent,
									textTitle: this.textTitle,
									textAddress: this.textAddress,
									dateTime: this.datetime,
									online:this.index,//默认为10+
									
								};
								
									param.imageList = this.imageList;
									
									//param.online=this.picker.index+11;
								let [publishMomentData, publishMomentError] = await httpUtils.postJson("/activity/submitActivity", param);
								uni.hideLoading();
							
								if (!publishMomentError) {
									uni.reLaunch({
										url: "../square/square"
									});
								}
						//}
					}
						
	        }
	    },
		}
</script>

<style>
	@import url("/colorui/main.css");
	@import url("/colorui/icon.css");
	@import url("/colorui/animation.css");
	.search-input {
		display: inline;
	},
	.textbeforeinput{
		
		margin-top: 16rpx;
		margin-left: 10rpx;
	},
	.oneRow{
		margin-top: 70rpx;
		margin-left: 25rpx;
		display: flex;
		flex-flow: row,nowrap;
	},
	/* 添加图片按钮 */
	.add-image {
		position: relative;
		width: 216rpx;
		height: 216rpx;
		background-color: #f7f7f7;
		margin-bottom: 22rpx;
		margin-right: 14rpx;
		transition: background-color 0.3s;
		border-radius:30rpx;
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
	
</style>
