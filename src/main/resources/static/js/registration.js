let regApi = Vue.resource("/api/auth/register")

Vue.component('reg-form', {
    data: function () {
        return {
            email: '',
            username: '',
            password: ''
        }
    },
    template: '<div>' +
        '<input type="text" placeholder="email" v-model="email">' +
        '<input type="text" placeholder="username" v-model="username">' +
        '<input type="text" placeholder="password" v-model="password">' +
        '<input type="button" value="Sign Up" @click="reg">' +
        '<a href="/auth/login">Sign In</a>' +
        '</div>',
    methods: {
        reg: function () {
            let user = {
                email: this.email,
                username: this.username,
                password: this.password
            };
            regApi.save(user).then(result => result.json().then(
                data => {
                    this.email = '';
                    this.username = '';
                    this.password = '';
                    window.location.replace("http://localhost:8080/auth/login");
                }))

        }
    }
})


let app = new Vue({
        el: '#regApp',
        template: '<reg-form>'
    }
)