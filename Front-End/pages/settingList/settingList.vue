<template>
	<view class="setting-root">
		<navigator class="setting-item" url="/pages/certifiCat/certifiCat">
			<image class="setting-item-icon" src="../../static/icon/setting-user-info.png" mode="aspectFill"></image>
			<view class="setting-item-title">
				学生认证
			</view>
			<view class="setting-item-go-to">
			</view>
		</navigator>

		<navigator class="setting-item" url="/pages/feedback/feedback">
			<image class="setting-item-icon" src="../../static/icon/setting-feedback.png" mode="aspectFill"></image>
			<view class="setting-item-title">
				反馈与举报
			</view>
			<view class="setting-item-go-to">
			</view>
		</navigator>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {

		}
	}
</script>

<style>
	.setting-root {
		background-color: #EDEDED;
		height: 100%;
	}

	.setting-item {
		position: relative;
		padding: 0 30rpx;
		display: flex;
		flex-direction: row;
		align-items: center;
		height: 108rpx;
		margin-bottom: 16rpx;
		background-color: white;
		color: #111111;
	}

	.setting-item:active {
		background-color: #D5D5D5;
	}

	.setting-item-icon {
		width: 50rpx;
		height: 50rpx;
		margin-right: 30rpx;
	}

	.setting-item-title {
		font-size: 32rpx;
	}

	.setting-item-go-to {
		position: absolute;
		top: 46rpx;
		right: 30rpx;
		width: 14rpx;
		height: 14rpx;
		border-top: 4rpx solid #ACACAC;
		border-right: 4rpx solid #ACACAC;
		transform: rotate(45deg);
	}
</style>
