import React, { useContext, useState } from 'react'
import { Modal, Row, Col, Button, Form } from 'react-bootstrap'
import './ItemModal.css'
import { MenuContext } from '../../contexts/MenuContext'

function ItemModal({ show, handleCloseModal, item }) {

    const { editInfo } = useContext(MenuContext)

    const [edittedItem, setItem] = useState({
        productId: item.productId,
        productName: item.productName,
        description: item.description,
        price: item.price,
        available: item.available,
        category: item.category.categoryId,
        imageUrl: item.imageUrl
    })

    const handleEditInfo = async (e) => {
        e.preventDefault();
        try {
            await editInfo(item.productId, edittedItem)
            console.log('editted item: ', edittedItem)
            handleCloseModal()
        }
        catch (err) {
            window.alert('Không thể chỉnh sửa món ăn')
        }
    }

    return (
        <Modal
            show={show}
            onHide={handleCloseModal}
            centered
            backdrop={true}
            keyboard={false}
            scrollable
            size="lg"
        >
            <Modal.Header closeButton />
            <Modal.Body>
                <Form onSubmit={handleEditInfo}>
                    <Row>

                        <Col xl={4}>
                            <div>
                                <img src={item.imageUrl} style={{ height: 'auto', width: '250px' }}></img>
                            </div>
                            <input type="file" />
                            <div style={{ marginBlock: '1em', display: 'flex', gap: '1em', alignItems: 'center' }}>
                                <Form.Check type='radio' name='availability' checked={edittedItem.available === true}
                                    required
                                    value={true}
                                    onChange={(e) => setItem({ ...edittedItem, available: true })} />
                                <Form.Label>Đưa vào bán</Form.Label>
                            </div>
                            <div style={{ marginBlock: '1em', display: 'flex', gap: '1em', alignItems: 'center' }}>
                                <Form.Check type='radio' name='availability' checked={edittedItem.available === false}
                                    required
                                    value={false}
                                    onChange={(e) => setItem({ ...edittedItem, available: false })} />
                                <Form.Label>Ngừng bán</Form.Label>
                            </div>
                        </Col>


                        <Col>
                            <Form.Group>
                                <Form.Label>Tên món ăn</Form.Label>
                                <Form.Control type='text' value={edittedItem.productName}
                                    onChange={(e) => setItem({ ...edittedItem, productName: e.target.value })}></Form.Control>
                                <Form.Label>Giá tiền</Form.Label>
                                <Form.Control type='text' value={edittedItem.price}
                                    onChange={(e) => setItem({ ...edittedItem, price: Number(e.target.value) })}></Form.Control>
                                <Form.Label>Mô tả</Form.Label>
                                <Form.Control type='text' value={edittedItem.description}
                                    onChange={(e) => setItem({ ...edittedItem, description: e.target.value })}></Form.Control>
                                <Form.Label>Phân loại</Form.Label>
                                <Form.Select
                                    value={edittedItem.category.categoryId}
                                    onChange={(e) => setItem({ ...edittedItem, category: Number(e.target.value) })}
                                >
                                    <option value={1}>Gà Giòn</option>
                                    <option value={2}>Mì Ý</option>
                                    <option value={3}>Nước / tráng miệng</option>
                                </Form.Select>
                            </Form.Group>
                            <Button type='submit' style={{marginTop:'1rem'}}>Chỉnh sửa</Button>
                        </Col>
                    </Row>
                </Form>
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
        </Modal>
    )
}

export default ItemModal
