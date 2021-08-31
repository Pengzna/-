<template>
	<view>
		<view class="container">
		    <!-- Left content -->
		    <view class="left">
		     
				<image src="../../static/forTest.png"></image>
				<image src="../../static/forTest.png"></image>
		    </view>
			<!-- 分割线 -->
			<view class="seperateLine">	</view>		
		    <!-- Right content -->
		    <view class="right">
				<text class="title">{{title}}</text>
				<text class="addressAndTime">地址：{{address}}</text>
				<text class="addressAndTime">时间：{{time}}</text>
				<a class="detail" herf="https://www.baidu.com/">详情传送门</a>
				<image :src="collectIcon" mode="widthFix" style="width: 80rpx;margin: 0;position: absolute;" @click="collect"></image>
				<text class="collect">👈点此收藏哟！</text>
		    </view>
		</view>
		
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title:"第二次话剧表演",
				address:"南京大学翻找周体育馆三楼四十巴亭",
				time:"2021-10-10 19:00:00",
				detail:"https://www.baidu.com/",
				//todo:收藏与否的初始值得从数据库中读取，下面是未收藏的图标，已收藏的图标是collected.png
				collectIcon:"../../static/icon/collect.png"
			}
		},

		methods: {
			//todo:点击收藏后，对应的信息会同步到有趣的活动里面，即相当于将活动收藏进有趣的活动，在收藏情况下再次点击，就从有趣的活动里面移除，二者都会改变用户的数据
			collect(){
				if(this.collectIcon == "../../static/icon/collect.png")
					{this.collectIcon = "../../static/icon/collected.png"}
				else
					{this.collectIcon = "../../static/icon/collect.png"}
			}
		}
	}
</script>

<style>
.container {
    display: flex;
}

.left {
    flex: 1;
	
},
.right{
	flex: 1.4;
	
},
.title{
	font-size: large;
	font-weight: 700;
	display: block;
	margin-bottom: 30rpx;
},
.addressAndTime{
	display: block;
	font-size: small;
	margin-bottom: 10rpx;
	color: #828282;
},
.detail{
	font-size: small;
	display: block;
	color: #AE4322;
	margin-bottom: 30rpx;
},
.seperateLine{
	padding:12px 6px 0px 6px;
	margin-left: 6px;
	margin-top: 5rpx;
	border-left: 2px solid #d5d0ce;
	font-size: 0;
	height: 360rpx;
	flex: 0.05;
},
.collect{
	color: #AE4322;
	position: relative;
	left: 100rpx;
	top: 10rpx;
},
image{
	margin: 40rpx;
	margin-bottom: 0;
	margin-top: 10rpx;
	width: 210rpx;
	height: 150rpx;
}
</style>
