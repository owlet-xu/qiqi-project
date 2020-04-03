<template>
  <div class="password-form">
    <el-form ref="formRef" :model="fromData">
      <el-form-item :label="$t('Login.OldPassword')" prop="passwordOld" :rules="rules.passwordOld">
        <el-input v-model="fromData.passwordOld" type="password" :placeholder="$t('Login.PasswordTip')"></el-input>
      </el-form-item>
      <el-form-item :label="$t('Login.NewPassword')" prop="password" :rules="rules.password">
        <el-input v-model="fromData.password" type="password" :placeholder="$t('Login.PasswordTip')"></el-input>
      </el-form-item>
      <el-form-item :label="$t('Login.ConfirmPassword')" prop="passwordConfirm" :rules="rules.passwordConfirm">
        <el-input v-model="fromData.passwordConfirm" type="password" :placeholder="$t('Login.PasswordTip')"></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" src="./password-form.ts"></script>
<style lang="scss" scoped src="./password-form.scss"></style>