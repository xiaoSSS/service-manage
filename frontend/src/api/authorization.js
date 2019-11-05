import request from '@/utils/request'

export function queryPage(queryParam) {
  return request({
    url: '/authorization/settings',
    method: 'post',
    data:queryParam
  })
}

export function saveOrUpdate(params) {
  return request({
    url: '/authorization/save',
    method: 'post',
    data:params
  })
}

export function deleteOne(id) {
  return request({
    url: '/authorization/delete',
    method: 'post',
    params:{
      id:id
    }
  })
}

export function updateSecretKey(id) {
  return request({
    url: '/authorization/update/secret',
    method: 'post',
    params:{
      id:id
    }
  })
}

export function recover(id){
  return request({
    url: '/authorization/recover',
    method: 'post',
    params:{
      id:id
    }
  })
}
