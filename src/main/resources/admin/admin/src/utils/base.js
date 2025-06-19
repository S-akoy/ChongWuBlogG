const base = {
    get() {
        return {
            url : "http://localhost:8080/springboot8v545ay0/",
            name: "springboot8v545ay0",
            // 退出到首页链接
            indexUrl: 'http://localhost:8080/springboot8v545ay0/front/dist/index.html'
        };
    },
    getProjectName(){
        return {
            projectName: "基于SpringBoot+MyBatis-Plus+Vue的宠物交流系统"
        } 
    }
}
export default base
