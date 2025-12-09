import { useContext, useState } from "react"
import { MenuContext } from "../contexts/MenuContext"
import { Button } from "react-bootstrap"
import ItemCard from "./ItemCard/ItemCard"
import AddItemModal from "./ItemModal/AddItemModal"
import './MenuManage.css'

function MenuManage_page() {

  const { menu } = useContext(MenuContext)
  const [show, setShow] = useState(false)

  return (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '30px' }}>
      <Button className="floating-add-btn"
        onClick={() => setShow(true)}
      >
        Thêm món vào menu
      </Button>
      {menu.map((item) => (
        <ItemCard key={item.productId} item={item} />
      ))}

      <AddItemModal show={show} handleCloseModal={setShow} />
    </div>
  )
}

export default MenuManage_page
