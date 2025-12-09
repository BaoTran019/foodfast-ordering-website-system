import React, { useContext, useState } from 'react'
import { Modal, Row, Col, Button, Form } from 'react-bootstrap'
import { MenuContext } from '../../contexts/MenuContext'
import './ItemModal.css'

function AddItemModal({ show, handleCloseModal }) {

    const { addProduct } = useContext(MenuContext)

    const [addedItem, setItem] = useState({
        productName: '',
        description: '',
        price: 0,
        available: false,
        category: 1,
        imageUrl: 'null'
    })

    const handleAddProduct = async (e) => {
        e.preventDefault();
        try {
            console.log('added item: ', addedItem)
            await addProduct(addedItem)
            handleCloseModal()
        }
        catch (err) {
            window.alert('Không thể thêm món ăn')
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
                <Form onSubmit={handleAddProduct}>
                    <Row>
                        <Col xl={4}>
                            <div>
                                <img alt='Thêm hình ảnh' style={{ height: 'auto', width: '250px' }}></img>
                            </div>
                            <input type="file" />
                            <div style={{ marginBlock: '1em', display: 'flex', gap: '1em', alignItems: 'center' }}>
                                <Form.Check type='radio' name='availability' checked={addedItem.available === true}
                                    required
                                    value={true}
                                    onChange={(e) => setItem({ ...addedItem, available: true })} />
                                <Form.Label>Đưa vào bán</Form.Label>
                            </div>
                            <div style={{ marginBlock: '1em', display: 'flex', gap: '1em', alignItems: 'center' }}>
                                <Form.Check type='radio' name='availability' checked={addedItem.available === false}
                                    required
                                    value={false}
                                    onChange={(e) => setItem({ ...addedItem, available: false })} />
                                <Form.Label>Ngừng bán</Form.Label>
                            </div>
                        </Col>

                        <Col>
                            <Form.Group>
                                <Form.Label>Tên món ăn</Form.Label>
                                <Form.Control type='text' placeholder='Tên gọi món ăn'
                                    onChange={(e) => setItem({ ...addedItem, productName: e.target.value })}></Form.Control>
                                <Form.Label>Giá tiền</Form.Label>
                                <Form.Control type='text' placeholder='Giá tiền'
                                    onChange={(e) => setItem({ ...addedItem, price: e.target.value })}></Form.Control>
                                <Form.Label>Mô tả</Form.Label>
                                <Form.Control type='text' placeholder='Mô tả món ăn'
                                    onChange={(e) => setItem({ ...addedItem, description: e.target.value })}></Form.Control>
                                <Form.Label>Phân loại</Form.Label>
                                <Form.Select
                                    onChange={(e) => setItem({ ...addedItem, category: Number(e.target.value) })}
                                >
                                    <option value={1}>Gà Giòn</option>
                                    <option value={2}>Mì Ý</option>
                                    <option value={3}>Nước / tráng miệng</option>
                                </Form.Select>
                            </Form.Group>
                            <Button type='submit' style={{marginTop:'1rem'}}>Thêm món ăn</Button>
                        </Col>
                    </Row>
                </Form>
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
        </Modal>
    )
}

export default AddItemModal
