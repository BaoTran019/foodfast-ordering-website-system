import { useContext, useState, useEffect } from "react";
import { Container, Row, Col, Image, Form, Button } from "react-bootstrap"
import { toast } from "react-toastify";
import { NavLink, useNavigate } from "react-router-dom"
import { AuthContext } from "../context/AuthenticationContext";
import { CartContext } from "../context/CartContext";
import { UserContext } from "../context/UserContext";
import { OrderContext } from "../context/OrderContext";
import './ProfilePage.css'
import avatar from '../assets/avatar/chicken_avatar.jfif'

function ProfilePage() {

    const { user, changeInfo } = useContext(UserContext)
    const { logOut } = useContext(AuthContext)
    const { removeAllItems } = useContext(CartContext)
    const { orders } = useContext(OrderContext)

    const completedCount = orders.length;

    const navigate = useNavigate()

    const handleLogOut = () => {
        toast.warning('Đã đăng xuất')
        removeAllItems()
        logOut();
        navigate('/')
        window.scrollTo(0, 0)
    }

    const [edittedInfo, setInfo] = useState({
        fullName: user.name,
        phone: user.phone,
        address: user.address,
        email: user.email
    })
    useEffect(() => {
        if (user) {
            setInfo({
                fullName: user.name || '',
                phone: user.phone || '',
                address: user.address || '',
                email: user.email || ''
            });
        }
    }, [user]);

    const handleEditInfo = async (e) => {
        e.preventDefault();
        try {
            console.log('editted info: ', edittedInfo)
            console.log('userId: ', user.id)
            await changeInfo(user.id, edittedInfo)
            toast.success('Chỉnh sửa thông tin thành công')
        }
        catch (err) {
            toast.error('Chỉnh sửa thông tin không thành công')
        }
    }

    return (
        <Container>
            <Row className="user-content"
                style={{ minHeight: '100vh', display: 'flex', justifyContent: 'center', alignContent: 'center', marginBottom:'10px' }}>
                <Col xl={4} xs={12} className="user-avatar" style={{ textAlign: 'center', marginBlock: 'auto' }}>
                    <Image
                        roundedCircle
                        src={avatar}
                        style={{ height: '300px' }}>
                    </Image>
                </Col>
                <Col xl={8}>
                    <Form onSubmit={handleEditInfo}>
                        <Form.Group>
                            <Form.Label>
                                Họ tên
                            </Form.Label>
                            <Form.Control type="text" value={edittedInfo.fullName}
                                onChange={(e) => setInfo({ ...edittedInfo, fullName: e.target.value })}></Form.Control>
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>
                                Số điện thoại
                            </Form.Label>
                            <Form.Control type="tel" value={edittedInfo.phone}
                                onChange={(e) => setInfo({ ...edittedInfo, phone: e.target.value })}></Form.Control>
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>
                                Địa chỉ
                            </Form.Label>
                            <Form.Control type="text" value={edittedInfo.address}
                                onChange={(e) => setInfo({ ...edittedInfo, address: e.target.value })}></Form.Control>
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>
                                Email
                            </Form.Label>
                            <Form.Control type="email" value={edittedInfo.email}
                                onChange={(e) => setInfo({ ...edittedInfo, email: e.target.value })}></Form.Control>
                        </Form.Group>
                        <Button type="submit" style={{ marginTop: '20px', position: 'right' }}>Chỉnh sửa thông tin</Button>
                    </Form>
                    <div style={{ marginTop: '30px', fontSize: 'xx-large', fontWeight: 'lighter' }}>
                        Tổng số đơn hàng bạn đã đặt:&nbsp;&nbsp;
                        <span style={{ color: '#ff6600', fontWeight: 'bold', fontSize: '3rem' }}>
                            {completedCount}
                        </span>
                        <NavLink style={{ marginLeft: '10px', fontSize: 'large', fontWeight: 'bold', color: '#ff6600' }}
                        to = '/orders'>Xem chi tiết</NavLink>
                    </div>
                    <Button style={{marginBlock:'10px'}} onClick={() => handleLogOut()}>Log out</Button>
                </Col>
            </Row>
        </Container>
    )
}

export default ProfilePage
