export default {
	baseUrl: 'http://localhost:8080/springboot8v545ay0/',
	name: '/springboot8v545ay0',
	indexNav: [
		{
			name: '用户',
			url: '/index/yonghu',
		},
		{
			name: '宠物知识',
			url: '/index/chongwuzhishi',
		},
		{
			name: '在线交流',
			url: '/index/forum'
		}, 
		{
			name: '公告资讯',
			url: '/index/news'
		},
	],
	cateList: [
		{
			name: '宠物知识',
			refTable: 'zhishifenlei',
			refColumn: 'zhishifenlei',
		},
		{
			name: '在线交流',
			refTable: 'forumtype',
			refColumn: 'typename',
		},
		{
			name: '公告资讯',
			refTable: 'newstype',
			refColumn: 'typename',
		},
	]
}
