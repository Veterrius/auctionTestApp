let loginApi = Vue.resource('/api/auth/login');
let jwtToken;

Vue.component('login-form', {
    data: function() {
        return {
            email:'',
            password:''
        }
    },
    template: "<div>" +
        "<div>Please login</div>" +
        "<input type='text' placeholder='Email' v-model='email'>" +
        "<input type='text' placeholder='Password' v-model='password'>" +
        "<input type='button' value='Login' @click='login'>" +
        "<a href='/auth/login'>Sign In</a>" +
        "</div>",
    methods: {
        login: function () {
            let request = {
                email: this.email,
                password: this.password
            }
            loginApi.save(request).then(result => result.json().then(
                data => {
                    this.email = '';
                    this.password = '';
                    jwtToken = data.token;
                    window.location.replace("http://localhost:8080/");
                }
            ))
        }
    }
});


let app = new Vue({
        el: '#loginApp',
        template: "<login-form>"
    }
)

