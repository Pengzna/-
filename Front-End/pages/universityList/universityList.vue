<template>
	<view >

<!-- 弹窗 -->
		<view v-if="notshow==0" v-show="ishide" @touchmove.stop.prevent="stopSlide">
			<!-- 遮罩层 -->
			<view class="mask" @click="cancel"></view>
			<!-- 内容区 -->
			<view class="tip">
				<image src="#" mode="widthFix">公告</image>
				<!-- <text class="txt1">公告</text> -->
				<text>{{noticeInfo.createTime}}</text>
				<!-- <text>更新内容</text> -->
				<text class="txt4">{{noticeInfo.description}}</text>
				<view class="tipbtn" @click="cancel">我知道了</view>
			</view>
		</view>
		<!--<view class="me-list" >
			<navigator class="me-item me-item-margin" url="" @click="pop">当前
				<image class="me-item-icon" src="../../static/icon/university.png" mode="aspectFill"></image>
				<view class="me-item-title">
					{{uniname}}
				</view>
				<view class="me-item-go-to">
				</view>
			</navigator>
			</view>-->
		
			<!-- 搜索框和背景图片 -->
			<image src="https://p.pstatp.com/origin/pgc-image/fbbdded8e14842aa854872fdc205acfe" class="picSize"></image>
			<!-- <uni-search-bar :radius="100" @confirm="search" @input="input" class="searchBar"></uni-search-bar> -->
			<view class="box">
			<view class="cu-bar search bg-white">
				<view class="search-form round">
					<text class="cuIcon-search"></text>
					<input @focus="InputFocus" @blur="InputBlur" :adjust-position="false" type="text" placeholder="搜索相关内容" confirm-type="search" v-model="textContent"></input>
				</view>
				<view class="action">
					<button class="cu-btn bg-green shadow-blur round" @click="searchmoment">搜索</button>
				</view>
			</view>
			</view>
			<!-- 标签框 -->
<!-- 			<tabControl class="tab-control" :titles="['全部','文体','公益','讲座','招新','其他']"></tabControl> -->
			<scroll-view scroll-x class="bg-red nav text-center">
				<view class="cu-item" :class="{active:index===TabCur}" v-for="(item,index) in titles" :key="index" @tap="tabSelect" :data-id="index">
					{{titles[index]}}
				</view>
			</scroll-view>
			
			
			<view class="lineee"></view>
	<moment v-for="(moment) in momentInfo.momentList" :moment="moment" momentPageType="LIST" v-on:updateMomentLikeCount="updateMomentLikeCount($event,moment)"
	 v-on:updateMomentHasLiked="updateMomentHasLiked($event,moment)">
	</moment>
	
	<!-- 现活动信息 -->
	<!-- 我的需求是在页面加载时，读取活动信息相关数据（对象类型）存入activityInfo，根据对象的数量决定调用activityUI的次数;将活动信息(下面第一个组件的属性)传给每个activityUI组件-->
	<!-- <activityUI title="" address="" detail="" isCollected=""></activityUI> -->
	<view class="activityList">
		<activityUI
			v-for="info in activityInfo" 
			:title="info.title" :address="info.address" :time="info.time"
			:detail="info.detail" :isCollected="info.isCollected"  :imgUrl="info.imgUrl"
		></activityUI>
	</view>
	
	
	
	
	</view>

</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';
	import moment from '../../components/moment/moment.vue';
	import activityUI from '../../components/activityUI/activityUI.vue';
	import tabControl from '../../components/tabControl/tabControl.vue';
	
	
	export default {
		components: {
			moment,
			activityUI,
			tabControl
			//zwyPopUp,
		},
		data() {
				return {
					noticeInfo: {},
					notshow:1,//控制公告弹窗的显示
					ishide:true,
					userId:1,//所有id均1开头，为传入参数时，模糊查询1就可以获得所有数据（临时解决办法，后续可以在后端修改，如果未传参则全部查询即可，跳过模糊查询）
					uniname:"",
					uniId:100001,//初始默认显示南京大学
					momentInfo: {
						pageNo: 1,
						pageSize: 20,
						momentList: []
					},
					zdmomentInfo: {
						pageNo: 1,
						pageSize: 20,
						zdmomentList: []
					},
					
					index:-1,
					// 页面类型：FRIEND_MOMENT_LIST，ACTIVITY_MOMENT_LIST，TOPIC_MOMENT_LIST
					pageType: "",
					
					// 演出id
					activityId: null,
		
					// 话题
					topicName: null,
					topic: {},
					TabCur: 0,
					titles:['全部','文体','公益','讲座','招新','其他'],
					textContent:"",
					//活动信息
					activityInfo1:[
						//下面信息仅供测试，不需要的话直接删掉就行
						{
							title:"tttt1",
							address:"aaaaa1",
							detail:"dddd1",
							time:"iiiii1",
							isCollected:false,
							imgUrl:"../../static/forTest.png",
						},
						{
							title:"tttt2",
							address:"aaaaa2",
							detail:"dddd2",
							time:"iiiii2",
							isCollected:false,
							imgUrl:"../../static/forTest.png",
						},
						{
							title:"tttt3",
							address:"aaaaa3",
							detail:"dddd3",
							time:"iiiii3",
							isCollected:false,
							imgUrl:"../../static/forTest.png",
						},
						{
							title:"tttt1",
							address:"aaaaa1",
							detail:"dddd1",
							time:"iiiii1",
							isCollected:false,
							imgUrl:"../../static/forTest.png",
						},
						{
							title:"tttt2",
							address:"aaaaa2",
							detail:"dddd2",
							time:"iiiii2",
							isCollected:false,
							imgUrl:"../../static/forTest.png",
						},
						{
							title:"tttt3",
							address:"aaaaa3",
							detail:"dddd3",
							time:"iiiii3",
							isCollected:false,
							imgUrl:"../../static/forTest.png",
						},
						{
							title:"tttt1",
							address:"aaaaa1",
							detail:"dddd1",
							time:"iiiii1",
							isCollected:false,
							imgUrl:"../../static/forTest.png",
						},
						{
							title:"tttt2",
							address:"aaaaa2",
							detail:"dddd2",
							time:"iiiii2",
							isCollected:false,
							imgUrl:"../../static/forTest.png",
						},
						{
							title:"tttt3",
							address:"aaaaa3",
							detail:"dddd3",
							time:"iiiii3",
							isCollected:false,
							imgUrl:"../../static/forTest.png",
						},
						{
							title:"tttt1",
							address:"aaaaa1",
							detail:"dddd1",
							time:"iiiii1",
							isCollected:false,
							imgUrl:"../../static/forTest.png",
						},
						{
							title:"tttt2",
							address:"aaaaa2",
							detail:"dddd2",
							time:"iiiii2",
							isCollected:false,
							imgUrl:"../../static/forTest.png",
						},
						{
							title:"tttt3",
							address:"aaaaa3",
							detail:"dddd3",
							time:"iiiii3",
							isCollected:false,
							imgUrl:"../../static/forTest.png",
						},
					],
					
					
				}
			},
			async onHide(){
				
				
			},
			async onLoad(options) {
				await this.loadnotice();
				await this.loadCountInfo();
				
				if(this.countInfo.commentUnread+this.countInfo.atUnread>0){
					this.countInfo.commentUnread=this.countInfo.atUnread+this.countInfo.commentUnread+" ";
					uni.setTabBarBadge({
					  index: 2,
					  text: this.countInfo.commentUnread
					})
				}
				if(options.userId){
					//console.log("--"+options.userId);
					this.uniId=options.userId;
				}else{
					this.userId=this.getLoginUser().userId;
					let [userInfoData] = await httpUtils.postJson("/user/getUserInfo", {
						id: this.userId
					});
					this.uniId=userInfoData.body.uniId;//活动用户所属大学的学校代码
					this.notshow=userInfoData.body.notificationUnread;
					//console.log("--"+userInfoData.body.notificationUnread);
					//this.userInfo = userInfoData.body;
					
				}
				
				if(this.uniId==1){
					this.uniId=100001;
				
					this.uniname="南京大学(未实名显示默认学校)";
				}else{
					let [data] = await httpUtils.postJson("/setting/queryUniversityById", {
						id: this.uniId
					});
					
					this.uniname=data.body.name;
				}
				
				
				// uni.getStorage({
				//                 key:'swiper_info',
				//                 success:(res)=>{
				//                     this.userId = res.data
				//                     console.log("546"+this.userId)
				//                 }
				//             });
						
							// uni.$on('update', event =>{
							//             this.userId=event.userid;
							//                 console.log("090"+this.userId);
											
							//             });
					
				
				this.pageType = options.pageType;
				this.activityId = options.activityId;
				this.topicName = options.topicName;
				//this.userId=options.userId;
				//console.log(options.userId);
				// 加载置顶moment数据
				// await this.loadZDMomentInfo();
				// 加载moment数据


				
				await this.loadMomentInfo();
				uni.$on('updateMomentHasFollowed', event => {
					for (let i in this.momentInfo.momentList) {
						let moment = this.momentInfo.momentList[i];
						if (moment.id === event.id) {
							moment.hasFollowed = event.hasFollowed;
							break;
						}
					}
				});
			},
			async onShow(){

			},
			async onPullDownRefresh() {
				this.momentInfo = {
					pageNo: 1,
					pageSize: 7,
					momentList: []
				};
				this.retrunInit(0);
				await this.loadMomentInfo();
				
				uni.stopPullDownRefresh();
			},

			async onReachBottom() {
				console.log("触底");
				await this.loadMomentInfo();
			},
		
			methods: {
				
			
				
				//加载公告
				async loadnotice(){
					let [noticeInfoData] = await httpUtils.postJson("/user/getNoticeInfo", {
						status:2
					});
					this.noticeInfo = noticeInfoData.body;
					console.log(this.noticeInfo)
				},
				// 数据相关函数
				// 加载个数数据
				async loadCountInfo() {
					let [countData] = await httpUtils.postJson("/user/getUserInfo", {});
					this.countInfo = countData.body;
					console.log(this.countInfo);
				},
				// 关闭弹窗
				async cancel() {
					this.ishide=false;
					// let that = this;
					 console.log("123");
					// that.$emit('cancel');
					if (this.notshow ==0) {
						await httpUtils.postJson("/user/updateUserInfo", {
							notificationUnread:1
						});
					}
				},
				// 确认事件
				confirm() {
					this.ishide=false;
					// let that = this;
					// that.$emit('confirm');
				},
				// 禁止页面滚动(规避警告)
				stopSlide() {
					return;
				},
				// ccc(){
				// 	// 在需要压缩时调用
				// 	uni.chooseImage({
				// 	    success: (chooseImageRes) => {
				// 	        const tempFilePaths = chooseImageRes.tempFilePaths;
				// 	        // compress
				// 	        const compressParams = {
				// 	            src: tempFilePaths[0], // 必选： 要压缩的图片地址
								
				// 	        }
				// 	        this.$refs.compress.compress(compressParams).then((res)=>{
				// 				console.log(res)
				// 			});
				// 	    }
				// 	});
					
				// },
				
				univer(){
					console.log("111");
					uni.navigateTo({
						url: `../../pages/universityList/universityList`
					});
				},
				getDetailzd(momentId){
					console.log("111");
					uni.navigateTo({
						url: `../../pages/zdmomentDetail/zdmomentDetail?momentId=${momentId}`
					});
				},
				// 事件相关函数
				updateMomentLikeCount(event, moment) {
					moment.likeCount = event;
				},
		
				updateMomentHasLiked(event, moment) {
					moment.hasLiked = event;
				},
		
				updateTopicCoverUrl() {
					let loginUser = uni.getStorageSync("loginUser");
					if (this.topic.createUserId !== loginUser.userId) {
						return;
					}
		
					uni.chooseImage({
						count: 1,
						sizeType: ['original'],
						sourceType: ['album'],
						success: async (res) => {
							let tempFilePath = res.tempFilePaths[0];
		
							uni.showLoading({
								title: '上传中...'
							});
							let [url] = await httpUtils.uploadFile(tempFilePath);
							uni.hideLoading();
		
							await httpUtils.postJson("/topic/updateTopicCoverUrl", {
								topicId: this.topic.id,
								coverUrl: url
							});
							this.topic.coverUrl = url;
						}
					});
				},
		
				// 加载置顶moment数据
				async loadZDMomentInfo() {
					let [data] = await httpUtils.postJson("/square/querySomeZDMoment", {
						pageNo: this.zdmomentInfo.pageNo,
						pageSize: this.zdmomentInfo.pageSize,
						userId: this.userId
					});
					this.zdmomentInfo.zdmomentList = this.zdmomentInfo.zdmomentList.concat(data.body);
					//console.log(this.zdmomentInfo.zdmomentList[0].url);
					this.zdmomentInfo.pageNo++;
				},
				// 数据相关函数
				// 加载moment数据
				async loadMomentInfo() {

					// uni.$on('update', event =>{
					//             this.userId=event.userid;
					//                 console.log(this.userId);
									
					//             });
					
					//if (this.pageType === 'FRIEND_MOMENT_LIST2') {//查关注的某个人的动态
							//通过like关键字搜索id前缀为uniId的所有用户的数据
							let [data] = await httpUtils.postJson("/square/querySomeMoment", {
								pageNo: this.momentInfo.pageNo,
								pageSize: this.momentInfo.pageSize,
								userId: this.uniId,
							});
							//this.momentInfo.momentList = data.body;
							this.momentInfo.momentList = this.momentInfo.momentList.concat(data.body);
							this.momentInfo.pageNo++;
							
							//console.log(this.momentInfo.momentList)
				},
				async loadDifferMomentInfo() {
							let [data] = await httpUtils.postJson("/square/queryDifferentMoment", {
								pageNo: 1,
								pageSize: 20,
								shareCount:this.index,
								userId: this.uniId,
								// userId: this.uniId,
								// activityId:this.index,
							});
							this.momentInfo.momentList = data.body;
							//this.momentInfo.momentList = this.momentInfo.momentList.concat(data.body);
							this.momentInfo.pageNo++;
							
							//console.log(this.momentInfo.momentList)
				},
				// 加载moment数据
				async loadNotifiInfo() {
				
					// uni.$on('update', event =>{
					//             this.userId=event.userid;
					//                 console.log(this.userId);
									
					//             });
					
					//if (this.pageType === 'FRIEND_MOMENT_LIST2') {//查关注的某个人的动态
							//通过like关键字搜索id前缀为uniId的所有用户的数据
							let [data] = await httpUtils.postJson("/square/querySomeMoment", {
								pageNo: this.momentInfo.pageNo,
								pageSize: this.momentInfo.pageSize,
								userId: this.index
							});
							
							this.momentInfo.momentList = this.momentInfo.momentList.concat(data.body);
							this.momentInfo.pageNo++;
					
				},
				pop(){
						uni.navigateTo({
							//delta: 1
							url: "/pages/cityuniversityList/cityuniversityList"
						});
				},
				async tabSelect(e) {
					this.TabCur = e.currentTarget.dataset.id;
					this.index=e.currentTarget.dataset.id;
					this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60;

					this.momentInfo.momentList=null;
					await this.loadDifferMomentInfo();
				},
				 retrunInit(e) {//标签页跟着切换
					this.TabCur = e;
					this.index=e;
					this.scrollLeft = (e) * 60;
				},
				InputFocus(e) {
					this.InputBottom = e.detail.height
				},
				InputBlur(e) {
					this.InputBottom = 0
				},
				async searchmoment(){
					this.momentInfo.momentList=null;
					let [data] = await httpUtils.postJson("/square/querySomeMoment", {
						pageNo: 1,
						pageSize: 20,
						textContent: this.textContent,
						userId: this.uniId,
						
					});
					this.momentInfo.momentList = data.body;
					//this.momentInfo.momentList = this.momentInfo.momentList.concat(data.body);
					this.momentInfo.pageNo++;
				}
				
			}
		}
</script>

<style>
	@import url("all.css");

/* ==================
          操作条
 ==================== */

.cu-bar {
	position: absolute;
	width: 70%;
	z-index: 2;
	/* margin-top: -100px; */
	display: flex;
	/* position: relative; */
	align-items: center;
	/* min-height: -100upx; */
	/* justify-content: space-between; */
}
.bg-white {
	color: #666666;
}
.cu-bar .search-form {
	opacity: 0.525;
	background-color: #f5f5f5;
	line-height: 64upx;
	height: 64upx;
	font-size: 24upx;
	color: #333333;
	flex: 1;
	display: flex;
	align-items: center;
	margin: 0 30upx;
}
.round {
	border-radius: 5000upx;
}
.cuIcon-search:before {
	content: "\e65c";
}
.cu-bar .action {
	display: flex;
	align-items: center;
	height: 100%;
	justify-content: center;
	max-width: 100%;
}
.cu-bar .search-form+.action {
	margin-right: 30upx;
}

.cu-bar .search-form input {
	flex: 1;
	padding-right: 30upx;
	height: 64upx;
	line-height: 64upx;
	font-size: 26upx;
	background-color: transparent;
}

.cu-bar .search-form [class*="cuIcon-"] {
	margin: 0 0.5em 0 0.8em;
}

.cu-bar .search-form [class*="cuIcon-"]::before {
	top: 0upx;
}

.cu-bar.fixed,
.nav.fixed {
	position: fixed;
	width: 100%;
	top: 1;
	z-index: 1024;
	box-shadow: 0 1upx 6upx rgba(0, 0, 0, 0.1);
}
.cu-btn {
	position: relative;
	border: 0upx;
	display: inline-flex;
	align-items: center;
	justify-content: center;
	box-sizing: border-box;
	padding: 0 30upx;
	font-size: 28upx;
	height: 64upx;
	line-height: 1;
	text-align: center;
	text-decoration: none;
	overflow: visible;
	margin-left: initial;
	transform: translate(0upx, 0upx);
	margin-right: initial;
}
.bg-green {
	opacity: 0.5;
	background-color:#C1000C;
	color: #ffffff;
}
.shadow-blur {
	position: relative;
}
.round {
	border-radius: 5000upx;
}
.cuIcon-searchlist:before {
	content: "\e6fe";
}

.cu-bar .search-form {
	background-color: #f5f5f5;
	line-height: 64upx;
	height: 64upx;
	font-size: 24upx;
	color: #333333;
	flex: 1;
	display: flex;
	align-items: center;
	margin: 0 30upx;
}

.cu-bar .search-form+.action {
	margin-right: 30upx;
}

.cu-bar .search-form input {
	flex: 1;
	padding-right: 30upx;
	height: 64upx;
	line-height: 64upx;
	font-size: 26upx;
	background-color: transparent;
}

.cu-bar .search-form [class*="cuIcon-"] {
	margin: 0 0.5em 0 0.8em;
}

.cu-bar .search-form [class*="cuIcon-"]::before {
	top: 0upx;
}


	scroll-view.cu-steps {
		display: block;
		white-space: nowrap;
	}
	
	scroll-view.cu-steps .cu-item {
		display: inline-block;
	}
	.nav .cu-item.cur {
		border-bottom: 4upx solid;
	}
	.nav .cu-item {
		height: 90upx;
		display: inline-block;
		line-height: 90upx;
		margin: 0 10upx;
		padding: 0 20upx;
	}
	.bg-red {
		background-color: #ffffff;
		color: #000000;
	}
	.nav {
		white-space: nowrap;
	}
	.text-center {
		text-align: center;
	}
	/* 弹窗 */
	.mask {
		z-index: 99;
		background: rgba(0, 0, 0, 0.6);
		position: fixed;
		bottom: 0;
		right: 0;
		left: 0;
		top: 0;
	}
	
	.tip {
		z-index: 999;
		width: 466rpx;
		display: flex;
		font-size: 30rpx;
		text-align: center;
		background: #FFFFFF;
		align-items: center;
		padding: 50rpx 35rpx;
		flex-direction: column;
		justify-content: center;
		border-radius: 16rpx;
		position: fixed;
		left: 50%;
		top: 50%;
		transform: translate(-50%, -50%);
	}
	
	.tip>image {
		width: 25rpx;
		height: 25rpx;
		border-radius: 50%;
		background: #4CD964;
		
	}
	
	.txt1 {
		font-size: 36rpx;
		margin: 70rpx 0 40rpx 0;
	}
	
	.tip>text {
		display: block;
	}
	
	.txt4 {
		margin: 40rpx 0;
		font-size: 24rpx;
		background: #F5F5F5;
		border-radius: 16rpx;
		padding: 16rpx 20rpx;
	}
	
	.tipbtn {
		width: 210rpx;
		height: 60rpx;
		font-size: 26rpx;
		line-height: 60rpx;
		text-align: center;
		border-radius: 30rpx;
		background: linear-gradient(-90deg, #FEEF43, #E9D81B);
	}
	.me-list{
			color: #0081ff;
			
	}
	.lineee {
		height: 3rpx;
		background-color: #e9e9e9;
	}
	/* 搜索框 */
	.search {
		display: flex;
		flex-direction: row;
		margin: -260rpx 0 0 150rpx;
		font-size: 30rpx;
	}

	.search-icon {
		width: 46rpx;
		height: 46rpx;
		margin-left: -8rpx;
	}

	.search-input {
		flex: 1;
		padding-left: 20rpx;
		padding-top: 2rpx;
	}

	.search-input:focus {
		padding-left: 20rpx;
	}

	.search-input-placeholder {
		font-size: 30rpx;
		padding-top: 2rpx;
	}

	/* 话题 */
	.topic-list {
		margin-top: 30rpx;
	}

	.topic-item {
		display: flex;
		flex-direction: row;
		margin-bottom: 30rpx;
	}

	.topic-cover {
		width: 140rpx;
		height: 140rpx;
		flex-shrink: 0;
		margin-right: 20rpx;
	}

	.topic-info {
		width: 530rpx;
	}

	.topic-name {
		font-size: 32rpx;
		color: #333;
		margin-bottom: 12rpx;
		overflow-x: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.topic-create-user {
		font-size: 24rpx;
		color: #3F3F3F;
		margin-bottom: 12rpx;
		overflow-x: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.topic-moment-count {
		font-size: 24rpx;
		color: #828282;
		overflow-x: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
	/*新增*/
	.friend-list-root {
		height: 100%;
	}
	
	/* 用户信息 */
	.friend-user-wrapper {
		background-color: white;
		padding-left: 46rpx;
		padding-right: 30rpx;
		display: flex;
		flex-direction: row;
		padding-top: 20rpx;
		position: relative;
		margin-bottom: 42rpx;
	}
	
	.friend-user-avatar {
		width: 100rpx;
		height: 100rpx;
		flex-shrink: 0;
		border-radius: 10rpx;
		margin-right: 24rpx;
		border-radius: 50%;
	}
	
	.friend-user-info {
		width: 580rpx;
	}
	
	.friend-user-user-name {
		margin-top: 10rpx;
		font-size: 32rpx;
		color: #262626;
		position: relative;
	}
	
	.friend-user-gender {
		margin-left: 10rpx;
		width: 28rpx;
		height: 28rpx;
	
	}
	
	.friend-user-description {
		margin-top: 6rpx;
		font-size: 24rpx;
		color: #7E7E7E;
	}
	
	.friend-user-has-followed-false {
		position: absolute;
		top: 48rpx;
		right: 30rpx;
	
		background-color: white;
		color: #DA261B;
		border: 2rpx solid #DA261B;
	
		width: 126rpx;
		height: 50rpx;
		font-size: 26rpx;
		letter-spacing: 2rpx;
		line-height: 50rpx;
		text-align: center;
		border-radius: 32rpx;
		padding: 0 6rpx;
	}
	
	.friend-user-has-followed-true {
		position: absolute;
		top: 48rpx;
		right: 30rpx;
	
		background-color: white;
		color: #999999;
		border: 2rpx solid #d8d8d8;
	
		width: 126rpx;
		height: 50rpx;
		font-size: 26rpx;
		letter-spacing: 2rpx;
		line-height: 50rpx;
		text-align: center;
		border-radius: 32rpx;
		padding: 0 6rpx;
	}
	
	.line {
		height: 2rpx;
		background-color: #f3f3f3;
		margin-top: -4rpx;
	}
	
	/* tab导航 */
	.tab-nav-main-wrapper {
		display: flex;
		flex-direction: row;
		justify-content: center;
		border-bottom: 2rpx solid #f3f3f3;
		margin-top: 10rpx;
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
		margin: 0 120rpx;
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
	
	.tab-control{
		z-index: 3;
		position: sticky;
		top: 0px;
		left: 0px;
		/* margin-top: 0px; */
		/* margin-left: 2%; */
		/* width: 96%; */
	}
		
	.picSize{
		/* position: absolute; */
		margin-left: 2%;
		width: 96%;
		height:140px;
		z-index: 0;
		
	}
	.searchBar{
		opacity: 0.7;
		left: 55px;
		position: absolute;
		width: 70%;
		z-index: 2;
		margin-top: 5px;
	}
	.activityList{
	}
	.active{
		font-weight:600;
		color:#FCCF67;
		border-bottom: 8upx solid;
	}
</style>
