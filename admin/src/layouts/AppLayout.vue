<template>
  <a-layout style="min-height: 100vh">
    <a-layout-sider v-model:collapsed="collapsed" width="260">
      <div class="logo">
        <a href="/">
          <span v-if="!collapsed" style="color: #FFF; font-size: 20px; text-align: center;margin: 0;">甘肃万云</span>
          <span v-if="collapsed" style="color: #FFF; font-size: 20px; text-align: center;margin: 0;">万云</span>
        </a>
      </div>
      <a-menu
        theme="dark"
        mode="inline"
        :openKeys="open"
        v-model:selectedKeys="select"
        @openChange="openChange"
        @select="selectMenu"
      >
        <template v-for="item in sidebar">
          <a-menu-item v-if="item.children === undefined" :key="item.name">
            <component v-bind:is="item.meta.icon" style="font-size: 18px;"/>
            <span>{{ item.name }}</span>
          </a-menu-item>
          <a-sub-menu v-if="item.children !== undefined" :key="item.name">
            <template #title>
              <span>
                <component v-bind:is="item.meta.icon" style="font-size: 18px;"/>
                <span>{{ item.name }}</span>
              </span>
            </template>
            <a-menu-item v-for="item2 in item.children" :key="item2.name">
              {{ item2.name }}
            </a-menu-item>
          </a-sub-menu>
        </template>
      </a-menu>
    </a-layout-sider>
    <a-layout>
      <a-layout-header style="background: #fff; padding: 0;">
        <menu-unfold-outlined
          v-if="collapsed"
          class="trigger"
          @click="() => (collapsed = !collapsed)"
        />
        <menu-fold-outlined v-else class="trigger" @click="() => (collapsed = !collapsed)"/>

        <div v-if="auth !== null" class="menu-top-right">

          <div style="display: inline-block; padding-right: 5px; margin-right: 5px; color: #afafaf;">
            在线人数： {{ online }}
          </div>

          <a-dropdown placement="bottomRight">
            <a class="ant-dropdown-link menu-drop-top" @click.prevent>
              用户名：{{ auth.username }}
              <DownOutlined />
            </a>
            <template #overlay>
              <a-menu>
                <a-menu-item @click="handleRedirect({path: '/mine'})" class="menu-top-right-item">
                  个人中心
                </a-menu-item>
                <a-menu-divider></a-menu-divider>
                <a-menu-item @click="handleLogout" class="menu-top-right-item">
                  退出登录
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>

      </a-layout-header>
      <a-layout-content>
        <router-view/>
      </a-layout-content>
      <a-layout-footer style="text-align: center">
        Admin ©2021 Created by GanSu WanYun
      </a-layout-footer>
    </a-layout>
  </a-layout>
</template>
<script>
import {
  UserOutlined,
  TeamOutlined,
  MenuUnfoldOutlined,
  MenuFoldOutlined,
  UnlockOutlined,
  LogoutOutlined,
  DownOutlined,
} from '@ant-design/icons-vue';
import {ref, reactive, onUnmounted} from 'vue';
import {useRouter, useRoute} from "vue-router";
import authorized from "@router/authorized";
import {useStore} from "vuex";
import token from "@utils/token";

export default {
  components: {
    UserOutlined,
    TeamOutlined,
    MenuUnfoldOutlined,
    MenuFoldOutlined,
    UnlockOutlined,
    LogoutOutlined,
    DownOutlined
  },

  setup() {
    const router = useRouter();
    const route = useRoute();
    const store = useStore();
    const permissions = store.state.auth === null ? [] : store.state.auth.permissions;
    const auth = store.state.auth;

    const select = ref([route.name]);
    const open = ref([]);
    const sidebar = reactive([]);

    // 在线人数
    const online = ref(0);

    // 处理侧栏
    const handleSidebar = () => {
      authorized.forEach(menu => {
        // 判断为一级菜单
        if (menu.children === undefined) {
          // 判断为侧栏且拥有权限
          if (menu.meta.isSidebar && (permissions.includes(menu.name) || !menu.meta.isPermission)) {
            sidebar.push(menu)
          }
        }

        // 判断为二级菜单
        if (menu.children !== undefined) {
          // 判断为侧栏且拥有权限
          if (menu.meta.isSidebar && (permissions.includes(menu.name) || !menu.meta.isPermission)) {
            let lsChild = [];
            menu.children.forEach(menu2 => {
              if (menu2.meta.isSidebar && (permissions.includes(menu2.name) || !menu2.meta.isPermission)) {
                lsChild.push(menu2);
              }
            })
            menu.children = lsChild;
            sidebar.push(menu);
          }
        }
      })
    }

    // 查找父Key
    const findParent = () => {
      authorized.forEach(item => {
        if (item.children !== undefined) {
          if (item.children.filter(function (i) {
            return i.name === select.value[0]
          }).length > 0) {
            open.value.push(item.name)
          }
        }
      })
    }

    // 选择菜单
    const selectMenu = ({key}) => {
      router.push({name: key})
      findParent()
    }

    // 展开菜单时，关闭上一个菜单
    const openChange = (openMenu) => {
      if (openMenu.length > 1) {
        open.value = [openMenu[openMenu.length - 1]];
      }
    }

    // 跳转页面
    const handleRedirect = (obj) => {
      router.push(obj)
      select.value = []
      open.value = []
    }

    // 退出登录
    const handleLogout = () => {
      store.dispatch('logout')
      router.push({path: '/auth/login'})
    }

    // 处理权限，菜单显示等
    handleSidebar()

    // 查找打开父级菜单
    findParent()

    // 连接ws服务
    const ws = ref(null);
    const tk = token.getToken()
    if (tk) {
      ws.value = new WebSocket(process.env.VUE_APP_SOCKET_URL + '?token=' + tk );
      ws.value.onopen = () => {
        // console.log('连接成功')
      }

      ws.value.onmessage = (event) => {
        let obj = JSON.parse(event.data);
        // 判断是哪种类型推送
        switch (obj.type) {
          case 'online':
            online.value = obj.count;
            break;
          default:
            console.log('未知的处理类型', obj)
        }
      }

      ws.value.onerror = () => {
        // console.error('ws错误', err);
      }

      ws.value.onclose = () => {
        // res.code状态码说明:
        // 正常关闭(1000), 离开(1001), 协议错误(1002), 不能接受(1003), 保留(1004), 无状态代码(1005), 异常关闭(1006), 不一致(1007),
        // 违反政策(1008), 太大(1009), 无扩展(1010), 意外情况(1011), 服务重启(1012), 稍后再试(1013), TLS握手失败(1015)。
      }
    }

    // 页面卸载执行
    onUnmounted(() => {

      // 判断ws状态正常，页面卸载时主动关闭ws。
      if (ws.value !== null) {
        if (ws.value.readyState === 1) {
          ws.value.close(1000, '用户退出');
        }
      }

    })

    return {
      collapsed: ref(false),
      open,
      select,
      sidebar,
      auth,
      online,

      selectMenu,
      openChange,
      handleRedirect,
      handleLogout
    }
  },
};
</script>
<style>
.ant-layout-header .trigger {
  font-size: 20px;
  line-height: 64px;
  padding: 0 24px;
  cursor: pointer;
  -webkit-transition: color .3s;
  transition: color .3s;
}

.ant-layout-sider-children .logo {
  padding: 16px;
  background: #002140;
}

.ant-layout-sider-children .ant-menu-root {
  padding: 16px 0;
}

.ant-layout-sider-children {
  -webkit-box-shadow: 2px 0 6px rgba(0, 21, 41, .35);
  box-shadow: 2px 0 6px rgba(0, 21, 41, .35);
}

.site-layout .site-layout-background {
  background: #fff;
}

[data-theme='dark'] .site-layout .site-layout-background {
  background: #141414;
}

.menu-top-right {
  float: right;
  margin-right: 20px;
  height: 64px;
}
.menu-drop-top {
  padding-left: 10px;
  padding-right: 10px;
  display: inline-block;
  height: 100%;
  color: rgba(0,0,0,.65);
}
.menu-top-right-item {
  width: 160px;
}
.menu-drop-top:hover, .menu-drop-top:visited {
  color: rgba(0,0,0,.65);
  background-color: rgba(0,0,0,.025);
}
</style>