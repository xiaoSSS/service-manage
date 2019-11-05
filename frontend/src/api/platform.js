import request from '@/utils/request'

export function getPlatformBranchInfo() {
  return request({
    url: '/platform/branch/list',
    method: 'get',
  })
}
