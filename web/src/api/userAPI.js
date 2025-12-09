import config from './config.json'

const { SERVER_API } = config
const BASE_URL = `${SERVER_API}/api/users`

export const getUser = async (userId) => {
    const res = await fetch(`${BASE_URL}/${userId}`)
    if (!res) throw new Error("Không lấy được user")
    const data = await res.json()
    return Array.isArray(data) ? data[0] : data;
}

export const changeInfo = async (userId, info) => {

    const auth = JSON.parse(localStorage.getItem("auth"))
    const token = auth?.token

    try {
        console.log('userId: ',userId)
        console.log('info: ',info)
        const res = await fetch(`${BASE_URL}/change-info/${userId}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({
                fullName: info.fullName,
                phone: info.phone,
                address: info.address,
                email: info.email
            }), // gửi object info
        })

        if (!res.ok) {
            throw new Error(`Error: ${res.status}`);
        }
    }

    catch (err) {
        console.log('Lỗi khi cập nhật thông tin:', err)
        throw err
    }
}