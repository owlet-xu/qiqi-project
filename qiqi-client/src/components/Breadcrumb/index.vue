<template>
  <el-breadcrumb class="app-breadcrumb" separator="/">
    <transition-group name="breadcrumb">
      <el-breadcrumb-item v-for="(item, index) in breadcrumbs" :key="item.path">
        <span v-if="item.redirect === 'noredirect' || index === breadcrumbs.length - 1" class="no-redirect">{{ $t(item.meta.title) }}</span>
        <a v-else @click.prevent="handleLink(item)">{{ $t(item.meta.title) }}</a>
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script lang="ts">
import * as pathToRegexp from 'path-to-regexp';
import { Component, Vue, Watch } from 'vue-property-decorator';
import { RouteRecord, Route } from 'vue-router';
import { Path, RouterPrefix, RouterName } from '@/router/router-types';

@Component({
  name: 'Breadcrumb'
})
export default class extends Vue {
  private breadcrumbs: RouteRecord[] = [];

  @Watch('$route')
  private onRouteChange(route: Route) {
    // if you go to the redirect page, do not update the breadcrumbs
    if (route.path.startsWith('/redirect/')) {
      return;
    }
    this.getBreadcrumb();
  }

  created() {
    this.getBreadcrumb();
  }

  private getBreadcrumb() {
    let matched = this.$route.matched.filter((item: RouteRecord) => item.meta && item.meta.title);
    const first = matched[0];
    if (!this.isHome(first)) {
      matched = [{ path: Path.Home, meta: { title: RouterPrefix(RouterName.Home) } } as RouteRecord].concat(matched);
    }
    this.breadcrumbs = matched.filter((item: RouteRecord) => {
      return item.meta && item.meta.title && item.meta.breadcrumb !== false;
    });
  }

  private isHome(route: RouteRecord) {
    const name = route && route.name;
    if (!name) {
      return false;
    }
    return name === RouterPrefix(RouterName.Home);
  }

  private pathCompile(path: string) {
    // To solve this problem https://github.com/PanJiaChen/vue-element-admin/issues/561
    const { params } = this.$route;
    const toPath = pathToRegexp.compile(path);
    return toPath(params);
  }

  private handleLink(item: any) {
    const { redirect, path } = item;
    if (redirect) {
      this.$router.push(redirect);
      return;
    }
    this.$router.push(this.pathCompile(path));
  }
}
</script>

<style lang="scss" scoped>
.el-breadcrumb__inner,
.el-breadcrumb__inner a {
  font-weight: 400 !important;
}

.app-breadcrumb.el-breadcrumb {
  display: inline-block;
  font-size: 14px;
  line-height: 50px;
  margin-left: 8px;

  .no-redirect {
    color: #97a8be;
    cursor: text;
  }
}
</style>
