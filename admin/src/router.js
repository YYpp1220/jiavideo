import Vue from "vue"
import Router from "vue-router"
import Login from "./views/login.vue"
import Admin from "./views/admin.vue"
import Welcome from "./views/admin/welcome.vue"
import Category from "./views/admin/category.vue"
import Course from "./views/admin/course.vue"
import Content from "./views/admin/content.vue"
import Teacher from "./views/admin/teacher.vue"
import Chapter from "./views/admin/chapter.vue"
import Section from "./views/admin/section.vue"
import File from "./views/file/file.vue"
import User from "./views/user/user.vue"

Vue.use(Router);

export default new Router({
    mode: "history",
    base: process.env.BASE_URL,
    routes: [{
        path: "*",
        redirect: "/login",
    }, {
        path: "",
        redirect: "/login",
    }, {
        path: "/login",
        component: Login
    }, {
        path: "/",
        name: "admin",
        component: Admin,
        meta: {
            loginRequire: true,
        },
        children: [{
            //子路由不需要斜杠
            path: "welcome",
            name: "welcome",
            component: Welcome,
        }, {
            path: "business/category",
            name: "business/category",
            component: Category,
        }, {
            path: "business/course",
            name: "business/course",
            component: Course,
        }, {
            path: "business/chapter",
            name: "business/chapter",
            component: Chapter,
        }, {
            path: "business/section",
            name: "business/section",
            component: Section,
        }, {
            path: "business/content",
            name: "business/content",
            component: Content,
        }, {
            path: "business/teacher",
            name: "business/teacher",
            component: Teacher,
        }, {
            path: "upload/file",
            name: "upload/file",
            component: File,
        }, {
            path: "system/user",
            name: "system/user",
            component: User,
        }]
    }]
});