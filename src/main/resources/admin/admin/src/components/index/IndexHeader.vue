<template>
	<div class="navbar">
		<div class="title">
			<span class="title-name">{{this.$project.projectName}}</span>
		</div>
		<el-dropdown class="dropdown-box" @command="handleCommand" trigger="click">
			<div class="el-dropdown-link">
				<el-image v-if="avatar" :src="avatar?this.$base.url + avatar : require('@/assets/img/avator.png')" fit="cover"></el-image>
				<span class="label">欢迎您，</span>
				<span class="nickname">{{this.$storage.get('adminName')}}</span>
				<span class="icon iconfont icon-xiala"></span>
			</div>
			<el-dropdown-menu class="top-el-dropdown-menu" slot="dropdown">
				<el-dropdown-item class="item1" :command="''">
					<span class="icon iconfont icon-home19"></span>
					首页
				</el-dropdown-item>
				<el-dropdown-item class="item2" :command="'center'">
					<span class="icon iconfont icon-touxiang03"></span>
					个人中心
				</el-dropdown-item>
				<el-dropdown-item v-if="changeHasChat()" class="item5" :command="'friend'">
					<span class="icon iconfont icon-fangdajing07"></span>
					好友列表
				</el-dropdown-item>
				<el-dropdown-item v-if="changeHasChat()" class="item6" :command="'chat'">
					<span class="icon iconfont icon-fangdajing07"></span>
					聊天记录
				</el-dropdown-item>
				<el-dropdown-item v-if="this.$storage.get('role')!='管理员'" class="item3" :command="'front'">
					<span class="icon iconfont icon-fanhui12"></span>
					退出到前台
				</el-dropdown-item>
				<el-dropdown-item class="item4" :command="'logout'">
					<span class="icon iconfont icon-fanhui13"></span>
					退出登录
				</el-dropdown-item>
			</el-dropdown-menu>
		</el-dropdown>
		<el-dialog :visible.sync="friendVisible" :title="'好友列表'" :append-to-body="true">
			<div class="z-box" :style='{"width":"70%","padding":"20px","margin":"0 auto"}'>
				<div class="section-content" v-for="item in friendList" :key="item.id"
					style="justify-content: space-between;" @click.stop="chatClick(item)">
					<div style="display: flex;align-items: center;">
						<img :src="item.picture?$base.url + item.picture:require('@/assets/img/avator.png')" style="width: 60px;border-radius: 50%;"
							alt="">
						<div
							style="margin: 0 0 0 10px;display: flex;flex-direction: column;align-items: flex-start;">
							<div :style='{"fontSize":"14px","color":"#000","flex":"1","fontWeight":"bold"}'
								class="item-style">{{item.name}}{{item.type==0?'申请添加您为好友':''}}</div>
						</div>
					</div>
					<div>
						<el-button v-if="item.type==0" @click.stop="confirmFriend(item)" size="small" type="success">同意</el-button>
						<el-button v-if="item.type==0" @click.stop="cancelFriend(item)" size="small" type="danger">拒绝</el-button>
						<el-button v-if="item.type==1" @click.stop="delFriend(item)" size="small" type="danger">删除</el-button>
					</div>
				</div>
				<div class="noList" v-if="!friendList.length">
					暂无好友
				</div>
			</div>
		</el-dialog>
		<el-dialog :visible.sync="recordVisible" :title="'聊天记录'" :append-to-body="true">
			<div class="z-box" :style='{"width":"70%","padding":"20px","margin":"0 auto"}'>
				<div class="section-content" v-for="item in recordList" :key="item.id" @click.stop="chatClick(item)">
					<div style="display: flex;align-items: center;">
						<img :src="item.picture?$base.url + item.picture:require('@/assets/img/avator.png')" style="width: 60px;border-radius: 50%;"
							alt="">
						<div style="margin: 0 0 0 10px;display: flex;flex-direction: column;align-items: flex-start;">
							<div :style='{"fontSize":"14px","color":"#000","flex":"1","fontWeight":"bold"}'
								class="item-style">{{item.name}}</div>
							<div :style='{"color":"#666","flex":"1","fontSize":"14px","lineHeight":"1.5","display":"flex","alignItems":"center"}'
								class="item-style">
								<div v-if="item.notreadnum" style="padding: 0 5px;height: 16px;border-radius: 50%;text-align: center;line-height: 16px;font-size: 12px;background: #f00;color:#fff;width: auto;margin: 0 2px 0 0">{{item.notreadnum}}</div>
								{{item.content.split('/').length&&item.content.split('/')[0]=='upload'?'[图片]':item.content}}
							</div>
						</div>
					</div>
					<div>
						<el-button @click.stop="chatDel(item)" size="small" type="danger">删除</el-button>
					</div>
				</div>
				<div class="noList" v-if="!recordList.length">
					暂无聊天记录
				</div>
			</div>
		</el-dialog>
		<el-dialog :visible.sync="chatVisible" @close="clearChat" :title="nowname" :append-to-body="true">
			<div class="chat-content" id="chat-content">
				<div v-bind:key="item.id" v-for="item in chatList">
					<div v-if="item.addtime" class="addtime">{{timeFormat(item.addtime)}}</div>
					<div v-if="item.uid==myid" class="right-content">
						<el-alert v-if="item.format==1" class="text-content" :title="item.content" :closable="false"
							type="warning"></el-alert>
						<el-image v-else fit="cover" :src="item.content?$base.url + item.content:''" style="width: 100px;height: 100px;" :preview-src-list="[item.content?$base.url + item.content:'']"></el-image>
						<img :src="avatar?$base.url + avatar:require('@/assets/img/avator.png')" alt="" style="width: 30px;border-radius: 50%;height: 30px;margin: 0 0 0 10px;" />
					</div>
					<div v-else class="left-content">
						<img :src="nowfpic?$base.url + nowfpic:require('@/assets/img/avator.png')" alt="" style="width: 30px;border-radius: 50%;height: 30px;margin: 0 10px 0 0;" />
						<el-alert v-if="item.format==1" class="text-content" :title="item.content" :closable="false"
							type="success"></el-alert>
						<el-image v-else fit="cover" :src="item.content?$base.url + item.content:''" style="width: 100px;height: 100px;" :preview-src-list="[item.content?$base.url + item.content:'']"></el-image>
					</div>
					<div class="clear-float"></div>
				</div>
			</div>
			<div slot="footer" class="dialog-footer">
				<el-input @keydown.enter.native="addChat(null)" v-model="chatForm.content" placeholder="请输入内容" style="width: calc(100% - 180px);float: left;">
				</el-input>
				<el-button :disabled="chatForm.content?false:true" type="primary" @click="addChat(null)">发送</el-button>
				<el-upload style="display: inline-block;margin: 0 0 0 6px;" class="upload-demo" :action="uploadUrl" :on-success="uploadSuccess"
				    :show-file-list="false">
				    <el-button type="success">上传图片</el-button>
				</el-upload>
			</div>
		</el-dialog>
	</div>
</template>

<script>
	import {
		Loading
	} from 'element-ui';
	import axios from 'axios';
	import timeMethod from '@/components/common/timeMethod'
	import {
		WebsocketMixin
	} from '@/mixins/WebsocketMixin'
	export default {
		mixins: [WebsocketMixin],
		data() {
			return {
				dialogVisible: false,
				ruleForm: {},
				user: null,
				nowtablename: '',
				friendList: [],
				friendVisible: false,
				recordList: [],
				recordVisible: false,
				chatVisible: false,
				nowfid: 0,
				nowfpic:'',
				nowname: '',
				chatList: [],
				chatForm: {
					content: ''
				},
				uploadUrl: this.$base.url + 'file/upload',
				hasChatList: [
					'yonghu',
				],
				sensitiveWordsArr: [],
			};
		},
		created() {
			this.getSensitiveWords()
		},
		computed: {
			avatar(){
				return this.$storage.get('headportrait')?this.$storage.get('headportrait'):''
			},
			myid(){
				return this.$storage.get('userid')?this.$storage.get('userid'):''
			},
		},
		mounted() {
		},
		methods: {
			getSensitiveWords(){
				this.$http.get('sensitivewords/detail/1').then(rs=>{
					this.sensitiveWordsArr = rs.data.data.content.split(',')
				})
			},
			changeHasChat(){
				let table = this.$storage.get('sessionTable')
				for(let x in this.hasChatList){
					if(this.hasChatList[x]==table){
						return true
					}
				}
				return false
			},
			handleCommand(name) {
				if (name == 'logout') {
					this.onLogout()
				} 
				else if (name == 'front') {
					this.onIndexTap()
				}
				else if (name == 'friend'){
					this.getFriendList()
				}
				else if (name == 'chat'){
					this.getRecordList()
				}
				else {
					let router = this.$router
					name = '/'+name
					router.push(name)
				}
			},
			getFriendList() {
				this.$http.get('friend/page', {
					params: {
						uid: this.$storage.get('userid')
					}
				}).then(res => {
					if (res.data && res.data.code == 0) {
						res.data.data.list.sort(function(a, b) {
							return (a.type - b.type)
						});
						let arr = []
						for (let x in res.data.data.list) {
							if (res.data.data.list[x].type != 2) {
								arr.push(res.data.data.list[x])
							}
						}
						this.friendList = arr
						this.friendVisible = true
					}
				})
			},
			confirmFriend(item) {
				this.$confirm('是否同意好友申请？')
					.then(_ => {
						this.$http.post('friend/add', {
							uid: item.fid,
							fid: this.$storage.get('userid'),
							type: 1,
							tablename: this.$storage.get('sessionTable'),
							name: this.$storage.get('adminName'),
							picture: this.$storage.get('headportrait'),
						}).then(res => {
							this.$http.post('friend/add', {
								uid: this.$storage.get('userid'),
								fid: item.fid,
								type: 1,
								tablename: item.tablename,
								name: item.name,
								picture: item.picture,
							}).then(res1 => {
								this.$http.post('friend/delete', [item.id]).then(res2 => {
									if (res2.data && res2.data.code == 0) {
										this.$message({
											message: '添加好友成功',
											type: 'success',
											duration: 1500,
											onClose: () => {
												this.getFriendList()
											}
										});
									}
								})
							})
						})
					}).catch(_ => {});
			},
			cancelFriend(item) {
				this.$confirm('是否拒绝此好友申请？')
					.then(_ => {
						this.$http.post('friend/delete', [item.id]).then(res2 => {
							if (res2.data && res2.data.code == 0) {
								this.$message({
									message: '操作成功！',
									type: 'success',
									duration: 1500,
									onClose: () => {
										this.getFriendList()
									}
								});
							}
						})
					}).catch(_ => {});
			},
			delFriend(row) {
				this.$confirm('是否删除此好友？').then(_ => {
					let arr = []
					let brr = []
					this.$http.get('chatmessage/list',{params: {
						page: 1,
						limit: 10000,
						uid: Number(this.$storage.get('userid')),
						fid: row.fid
					}}).then(rs=>{
						if(rs.data.data.list.length){
							for(let x in rs.data.data.list){
								arr.push(rs.data.data.list[x].id)
							}
						}
						this.$http.get('chatmessage/list',{params: {
							page: 1,
							limit: 10000,
							uid: row.fid,
							fid: Number(this.$storage.get('userid'))
						}}).then(rs2=>{
							if(rs2.data.data.list.length){
								for(let x in rs2.data.data.list){
									arr.push(rs2.data.data.list[x].id)
								}
							}
							if(arr.length){
								this.$http.post('chatmessage/delete',arr).then(()=>{})
							}
							this.$http.get('friend/list',{params: {
								page: 1,
								limit: 10000,
								uid: row.fid,
								fid: Number(this.$storage.get('userid'))
							}}).then(rs3=>{
								if(rs3.data.data.list.length){
									for(let x in rs3.data.data.list){
										brr.push(rs3.data.data.list[x].id)
									}
								}
								this.$http.get('friend/list',{params: {
									page: 1,
									limit: 10000,
									fid: row.fid,
									uid: Number(this.$storage.get('userid'))
								}}).then(rs4=>{
									if(rs4.data.data.list.length){
										for(let x in rs4.data.data.list){
											brr.push(rs4.data.data.list[x].id)
										}
									}
									this.$http.post('friend/delete',brr).then(()=>{
										this.$message.success('删除成功')
										this.getFriendList()
									})
								})
							})
						})
					})
				}).catch(_ => {});
			},
			getRecordList() {
				this.$http.get('friend/page2', {
					params: {
						uid: this.$storage.get('userid'),
						type: 2
					}
				}).then(res => {
					if (res.data && res.data.code == 0) {
						this.recordList = res.data.data.list
						this.recordVisible = true
					}
				})
			},
			chatClick(row) {
				if (row.type == 0) {
					return false
				}
				this.nowtablename = row.tablename
				this.nowfid = row.fid
				this.nowfpic = row.picture
				this.nowname = row.name
				this.initWebSocket(this.nowfid)
				this.getChatList()
				this.chatVisible = true
			},
			websocketOnopen: function() {
				
			},
			websocketOnmessage:function(e) {
				this.getChatList()
			},
			getChatList() {
				this.$http.get('chatmessage/mlist', {
					params: {
						page: 1,
						limit: 1000,
						uid: this.$storage.get('userid'),
						fid: this.nowfid
					}
				}).then(res => {
					if (res.data && res.data.code == 0) {
						this.chatList = this.formatMessages(res.data.data.list)
						let div = document.getElementsByClassName('chat-content')[0]
						setTimeout(() => {
							if (div)
								div.scrollTop = div.scrollHeight
						}, 0)
					}
				})
			},
			chatDel(row){
				this.$confirm('是否删除聊天记录？').then(_ => {
					let arr = []
					let brr = []
					this.$http.get('chatmessage/list',{params: {
						page: 1,
						limit: 10000,
						uid: Number(this.$storage.get('userid')),
						fid: row.fid
					}}).then(rs=>{
						if(rs.data.data.list.length){
							for(let x in rs.data.data.list){
								arr.push(rs.data.data.list[x].id)
							}
						}
						this.$http.get('chatmessage/list',{params: {
							page: 1,
							limit: 10000,
							uid: row.fid,
							fid: Number(this.$storage.get('userid'))
						}}).then(rs2=>{
							if(rs2.data.data.list.length){
								for(let x in rs2.data.data.list){
									arr.push(rs2.data.data.list[x].id)
								}
							}
							this.$http.post('chatmessage/delete',arr).then(()=>{
								this.$http.get('friend/list',{params: {
									page: 1,
									limit: 10000,
									uid: Number(this.$storage.get('userid')),
									fid: row.fid,
									type: 0
								}}).then(rs3=>{
									if(rs3.data.data.list.length){
										for(let x in rs3.data.data.list){
											brr.push(rs3.data.data.list[x].id)
										}
									}
									this.$http.get('friend/list',{params: {
										page: 1,
										limit: 10000,
										uid: row.fid,
										fid: Number(this.$storage.get('userid')),
										type: 0
									}}).then(rs4=>{
										if(rs4.data.data.list.length){
											for(let x in rs4.data.data.list){
												brr.push(rs4.data.data.list[x].id)
											}
										}
										this.$http.get('friend/list',{params: {
											page: 1,
											limit: 10000,
											uid: Number(this.$storage.get('userid')),
											fid: row.fid,
											type: 2
										}}).then(rs5=>{
											if(rs5.data.data.list.length){
												for(let x in rs5.data.data.list){
													brr.push(rs5.data.data.list[x].id)
												}
											}
											this.$http.get('friend/list',{params: {
												page: 1,
												limit: 10000,
												uid: row.fid,
												fid: Number(this.$storage.get('userid')),
												type: 2
											}}).then(rs6=>{
												if(rs6.data.data.list.length){
													for(let x in rs6.data.data.list){
														brr.push(rs6.data.data.list[x].id)
													}
												}
												this.$http.post('friend/delete',brr).then(()=>{
													this.$message.success('删除成功')
													this.getRecordList()
												})
											})
										})
									})
								})
							})
						})
					})
				}).catch(_ => {});
			},
			formatMessages(messages) {
				let lastTime = null;
				messages.forEach((message, index) => {
					const currentTime = new Date(message.addtime).getTime();
					if (lastTime !== null) {
						const timeDiff = (currentTime - lastTime) / 1000 / 60; // 转换为分钟
						if (timeDiff < 3) {
							message.addtime = ''; // 如果小于3分钟，不显示时间
						}
					}
					lastTime = currentTime;
				});
				return messages;
			},
			timeFormat(time) {
				const Time = timeMethod.getTime(time).split("T");
				//当前消息日期属于周
				const week = timeMethod.getDateToWeek(time);
				//当前日期0时
				const nti = timeMethod.setTimeZero(timeMethod.getNowTime());
				//消息日期当天0时
				const mnti = timeMethod.setTimeZero(timeMethod.getTime(time));
				//计算日期差值
				const diffDate = timeMethod.calculateTime(nti, mnti);
				//本周一日期0时 （后面+1是去除当天时间）
				const fwnti = timeMethod.setTimeZero(timeMethod.countDateStr(-timeMethod.getDateToWeek(timeMethod
					.getNowTime()).weekID + 1));
				//计算周日期差值
				const diffWeek = timeMethod.calculateTime(mnti, fwnti);

				if (diffDate === 0) { //消息发送日期减去当天日期如果等于0则是当天时间
					return Time[1].slice(0, 5);
				} else if (diffDate < 172800000) { //当前日期减去消息发送日期小于2天（172800000ms）则是昨天-  一天最大差值前天凌晨00:00:00到今天晚上23:59:59
					return "昨天 " + Time[1].slice(0, 5);
				} else if (diffWeek >= 0) { //消息日期减去本周一日期大于0则是本周
					return week.weekName;
				} else { //其他时间则是日期
					return Time[0].slice(5, 10);
				}
			},
			clearChat(){
				this.websocketOnclose();
				this.chatList = []
				this.getRecordList()
			},
			uploadSuccess(res) {
				if (res.code == 0) {
					this.addChat('upload/' + res.file);
				}
			},
			addChat(ask=null) {
				if (!this.chatList.length) {
					this.$http.post('friend/add', {
						uid: this.$storage.get('userid'),
						fid: this.nowfid,
						name: this.nowname,
						picture: this.nowfpic,
						type: 2,
						tablename: this.nowtablename
					}).then(res => {
						this.$http.post('friend/add', {
							uid: this.nowfid,
							fid: this.$storage.get('userid'),
							type: 2,
							tablename: this.$storage.get('sessionTable'),
							name: this.$storage.get('adminName'),
							picture: this.$storage.get('headportrait'),
						}).then(res1 => {
				
						})
					})
				}
				if(!ask) {
					for(var i=0; i<this.sensitiveWordsArr.length; i++){
						//全局替换
						var reg = new RegExp(this.sensitiveWordsArr[i],"g");
						//判断内容中是否包括敏感词
						if (this.chatForm.content.indexOf(this.sensitiveWordsArr[i]) > -1) {
							// 将敏感词替换为 **
							this.chatForm.content = this.chatForm.content.replace(reg,"**");
						}
					}
				}
				this.$http.post('chatmessage/add', {
					uid: this.$storage.get('userid'),
					fid: this.nowfid,
					content: ask?ask:this.chatForm.content,
					format: ask?2:1
				}).then(res2 => {
					this.websocketSend(ask?ask:this.chatForm.content)
					this.chatForm = {
						content: ''
					}
					this.getChatList()
				})
			},
			onLogout() {
				let storage = this.$storage
				let router = this.$router
				storage.clear()
				this.$store.dispatch('tagsView/delAllViews')
				router.replace({
					name: "login"
				});
			},
			onIndexTap(){
				localStorage.setItem("frontToken", localStorage.getItem("Token"));
				localStorage.setItem("frontRole", localStorage.getItem("role"));
				localStorage.setItem("frontSessionTable", localStorage.getItem("sessionTable"));
				localStorage.setItem("frontHeadportrait", localStorage.getItem("headportrait"));
				localStorage.setItem("UserTableName", localStorage.getItem("sessionTable"));
				localStorage.setItem("frontUserid", localStorage.getItem("userid"));
				localStorage.setItem("username", localStorage.getItem("adminName"));
				window.location.href = `${this.$base.indexUrl}`
			},
		}
	};
</script>


<style lang="scss" scoped>
	.navbar {
		.title {
			top: 13px;
			left: 250px;
			display: block;
			position: absolute;
			.title-name {
				padding: 0;
				color: #1a3194;
				font-weight: 600;
				font-size: 18px;
				line-height: 44px;
				float: left;
			}
		}
		.dropdown-box {
			color: inherit;
			display: flex;
			font-size: inherit;
			right: 0px;
			.el-dropdown-link {
				display: flex;
				align-items: center;
				.el-image {
					border-radius: 100%;
					margin: 0 10px;
					object-fit: cover;
					display: inline-block;
					width: 42px;
					height: 42px;
				}
				.label {
					color: inherit;
					display: none;
					font-size: inherit;
					line-height: 32px;
				}
				.nickname {
					color: inherit;
					font-size: inherit;
					line-height: 32px;
				}
				.iconfont {
					margin: 0 0 0 5px;
					color: rgba(255,255,255,.6);
					font-size: 14px;
				}
			}
		}
	}
	.top-el-dropdown-menu {
		border: 1px solid #EBEEF5;
		border-radius: 4px;
		padding: 10px 0;
		box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
		margin: 18px 0;
		background: #FFF;
		li.el-dropdown-menu__item.item1 {
			cursor: pointer;
			padding: 0 20px;
			margin: 0;
			outline: 0;
			color: #606266;
			background: #fff;
			font-size: 14px;
			line-height: 36px;
			list-style: none;
			.iconfont {
				margin: 0 4px 0 0;
				color: #000;
				font-size: 14px;
			}
		}
		li.el-dropdown-menu__item.item1:hover {
			background: #ecf5ff;
		}
		li.el-dropdown-menu__item.item2 {
			cursor: pointer;
			padding: 0 20px;
			margin: 0;
			outline: 0;
			color: #606266;
			background: #fff;
			font-size: 14px;
			line-height: 36px;
			list-style: none;
			.iconfont {
				margin: 0 4px 0 0;
				color: #000;
				font-size: 14px;
			}
		}
		li.el-dropdown-menu__item.item2:hover {
			background: #ecf5ff;
		}
		li.el-dropdown-menu__item.item3 {
			cursor: pointer;
			padding: 0 20px;
			margin: 0;
			outline: 0;
			color: #606266;
			background: #fff;
			font-size: 14px;
			line-height: 36px;
			list-style: none;
			.iconfont {
				margin: 0 4px 0 0;
				color: #000;
				font-size: 14px;
			}
		}
		li.el-dropdown-menu__item.item3:hover {
			background: #ecf5ff;
		}
		li.el-dropdown-menu__item.item4 {
			cursor: pointer;
			padding: 0 20px;
			margin: 0;
			color: #606266;
			background: #fff;
			font-size: 14px;
			line-height: 36px;
			list-style: none;
			.iconfont {
				margin: 0 4px 0 0;
				color: #000;
				font-size: 14px;
			}
		}
		li.el-dropdown-menu__item.item4:hover {
			background: #ecf5ff;
		}
		li.el-dropdown-menu__item.item5 {
			cursor: pointer;
			padding: 0 20px;
			margin: 0;
			outline: 0;
			color: #606266;
			background: #fff;
			font-size: 14px;
			line-height: 36px;
			list-style: none;
			.iconfont {
				margin: 0 4px 0 0;
				color: #000;
				font-size: 14px;
			}
		}
		li.el-dropdown-menu__item.item5:hover {
			background: #ecf5ff;
		}
		li.el-dropdown-menu__item.item6 {
			cursor: pointer;
			padding: 0 20px;
			margin: 0;
			outline: 0;
			color: #606266;
			background: #fff;
			font-size: 14px;
			line-height: 36px;
			list-style: none;
			.iconfont {
				margin: 0 4px 0 0;
				color: #000;
				font-size: 14px;
			}
		}
		li.el-dropdown-menu__item.item6:hover {
			background: #ecf5ff;
		}
	}
	.section-content {
		cursor: pointer;
		padding: 20px;
		box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.3);
		margin: 0 0 20px;
		color: #333;
		background: #fff;
		display: flex;
		width: 100%;
		border-color: #efefef;
		border-width: 0;
		align-items: center;
		border-style: solid;
		position: relative;
		justify-content: space-between;
	}
	
	.section-content:hover {
		color: #fff;
		background: #DF847F10;
	}
	.chat-content {
		padding-bottom: 20px;
		width: 100%;
		margin-bottom: 10px;
		max-height: 300px;
		height: 300px;
		overflow-y: scroll;
		border: 1px solid #eeeeee;
		background: #fff;
	
		.addtime {
			width: 100%;
			text-align: center;
			font-size: 12px;
		}
	
		.left-content {
			float: left;
			margin-bottom: 10px;
			padding: 10px;
			max-width: 80%;
			display: flex;
			align-items: center;
		}
	
		.right-content {
			float: right;
			margin-bottom: 10px;
			padding: 10px;
			max-width: 80%;
			display: flex;
			align-items: center;
		}
	}
	
	.clear-float {
		clear: both;
	}
	.noList {
		color: #9e9e9e;
		width: 100%;
		text-align: center;
		padding: 60px 0;
	}
</style>
