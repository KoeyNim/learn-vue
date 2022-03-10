let vu;
$(document).ready(function() {
    vu = new Vue({
        el: '#page',
        data: {
            member:{}
        },
        methods: {
            signIn() {
                this.$validator.validateAll().then(success => {
                if(success) {
                let form = new FormData();
                form.append('userId', this.member.userId);
                form.append('userPwd', this.member.userPwd);

                //var token = $("meta[name='_csrf']").attr("content");
                //var header = $("meta[name='_csrf_header']").attr("content");
                axios.post(API_VERSION + '/login/security', form, {/*headers: {[header]: token}*/})
                .then(response => {
                    console.log(response);
                    location.href = "/board";
                })
                .catch(error => {
                    alert(error.message);
                    if (error.response) {
                      // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
                      console.log(error.response.data);
                      console.log(error.response.status);
                      console.log(error.response.headers);
                    }
                    console.log(error.config);
                });
                    } else {
                            alert("입력 정보가 맞지 않습니다. 아이디와 비밀번호를 다시 입력해주세요.");
                    }
                });
            }
        }
    });
});