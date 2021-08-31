<template>
	<view>
		<view class="container">
		    <!-- Left content -->
		    <view class="left">
		     
				<image :src="imgUrl" class="activityImg"></image>
				<!-- <image src="../../static/forTest.png"></image> -->
		    </view>
			<!-- 分割线 -->
			<view class="seperateLine">	</view>		
		    <!-- Right content -->
		    <view class="right">
				<text class="title">{{title}}</text>
				<text class="addressAndTime">地址：{{address}}</text>
				<text class="addressAndTime">时间：{{time}}</text>
				<a class="detail" herf="https://www.baidu.com/" style="position: absolute;">点我查看详情</a>
				<image :src="collectIcon" mode="widthFix" style="width: 40rpx;margin: 0;position: relative;left: 280rpx;" @click="collect"></image>
				
		    </view>
		</view>
		
		
	</view>
</template>

<script>
	export default {
		name:"activityUI",
		data() {
			return {
				
			}
		},
		
		//发现页面传来的数据如下
		props:{
			
				//以下四个是活动活动发布者填写而后存入数据库，而后从数据库读取到发现页面
				title:{
					type:String,
					default:"该用户未填写标题"
				},
				address:{
					type:String,
					default:"该用户未填写地址"
				},
				time:{
					type:String,
					default:"该用户未填写时间"
				},
				detail:{
					type:String,
					default:""
				},
				//下面的是普通用户操作导致收藏状态变更而存入数据库，而后从数据库读取到发现页面
				isCollected:{
					type:Boolean,
					default:false
				},
				imgUrl:{
					type:String,
					default:"../../static/icon/search.jpg", 
				}
			
		},
		
		methods: {
			//todo:点击收藏后，对应的信息会同步到有趣的活动里面，即相当于将活动收藏进有趣的活动，在收藏情况下再次点击，就从有趣的活动里面移除，二者都会改变用户的数据
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
.container {
    display: flex;
	margin-top: 30rpx;
	width: 96%;
	margin-left: 2%;
	height: 230rpx;
	background-color: #FFFFFF;
	border-radius: 10rpx;
	box-shadow: 0px 2px 2px #cdcfcf;
}

.left {
    flex: 1;
	
},
.right{
	flex: 1.4;
	
},
.title{
	font-size: medium;
	font-weight: 700;
	display: block;
	margin-bottom: 10rpx;
},
.addressAndTime{
	display: block;
	font-size: small;
	margin-bottom: 10rpx;
	color: #828282;
},
.detail{
	font-size: small;
	text-decoration: underline;
	display: block;
	color: #B97A6E;
	margin-bottom: 30rpx;
},
.seperateLine{
	padding:12px 6px 0px 6px;
	margin-left: 6px;
	margin-top: 5rpx;
	border-left: 2px solid #d5d0ce;
	font-size: 0;
	height: 180rpx;
	flex: 0.05;
},
.collect{
	color: #AE4322;
	position: relative;
	left: 100rpx;
	top: 10rpx;
},
.activityImg{
	margin: 40rpx;
	margin-bottom: 0;
	margin-top: 30rpx;
	width: 210rpx;
	height: 150rpx;
	border-radius: 10rpx;
}
</style>
